package edu.nbu.services;

import edu.nbu.entities.Apartment;
import edu.nbu.entities.Building;
import edu.nbu.entities.Fee;
import edu.nbu.enums.FeeStatus;
import edu.nbu.exceptions.FeesAlreadyIssued;
import edu.nbu.repositories.FeeRepository;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

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
}
