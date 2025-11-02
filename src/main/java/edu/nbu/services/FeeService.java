package edu.nbu.services;

import edu.nbu.entities.Apartment;
import edu.nbu.entities.Building;
import edu.nbu.entities.Fee;
import edu.nbu.enums.FeeStatus;
import edu.nbu.exceptions.FeesAlreadyIssued;
import edu.nbu.exceptions.crud.CannotRetrieveResourceException;
import edu.nbu.repositories.FeeRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

@Singleton
public class FeeService {
    @Inject
    BuildingService buildingService;

    @Inject
    ApartmentService apartmentService;

    @Inject
    FeeRepository feeRepository;

    public void issueFees(YearMonth period, Long buildingId) {
        List<Fee> fees = feeRepository.findByApartmentFloorBuildingIdAndPeriod(buildingId, period);

        if (!fees.isEmpty()) {
            throw new FeesAlreadyIssued();
        }

        List<Apartment> apartments = apartmentService.list(buildingId);

        apartments.forEach(apartment -> {
            Fee fee = new Fee();
            fee.setApartment(apartment);
            fee.setPeriod(period);
            fee.setAmount(calculateFeeAmount(apartment));
            fee.setStatus(FeeStatus.PENDING);
            feeRepository.save(fee);
        });
    }

    @Transactional
    public void payFee(Long feeId) {
        Fee fee = findById(feeId);
        fee.setStatus(FeeStatus.PAID);
        fee.setPaidOn(Instant.now());
        feeRepository.save(fee);
    }

    public Fee findById(Long feeId) {
        return feeRepository.findById(feeId)
                .orElseThrow(() -> new CannotRetrieveResourceException("Fee not found"));
    }

    private Integer calculateFeeAmount(Apartment apartment) {
        Building building = apartment.getFloor().getBuilding();

        int total = 0;

        total += (BigDecimal.valueOf(apartment.getArea())
                .multiply(new BigDecimal(building.getFeePerSqMRaw())))
                .intValue();

        total += building.getFeePerResidentRaw() * apartment.getNumberOfResidents();
        total += building.getFeePerPetRaw() * apartment.getNumberOfPets();

        return total;
    }

    public List<Fee> list(Long buildingId) {
        return feeRepository.findByApartmentFloorBuildingId(buildingId);
    }
}
