package edu.nbu.services;

import edu.nbu.entities.Apartment;
import edu.nbu.entities.Building;
import edu.nbu.entities.Floor;
import edu.nbu.exceptions.crud.CannotCreateResourceException;
import edu.nbu.exceptions.crud.CannotRetrieveResourceException;
import edu.nbu.exceptions.crud.CannotUpdateResourceException;
import edu.nbu.repositories.ApartmentRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

public class ApartmentService {
    @Inject
    ApartmentRepository apartmentRepository;

    @Inject
    BuildingService buildingService;

    @Inject
    FloorService floorService;

    public Apartment create(Long buildingId, String name, Integer floorName, Float area, Integer numberOfResidents, Integer numberOfPets) {
        Building building = buildingService.findById(buildingId);
        Floor floor = floorService.firstOrCreate(building, floorName);


        if (apartmentRepository.existsByNameAndFloor(name, floor)) {
            throw new CannotCreateResourceException("Apartment with the given name already exists on the specified floor.");
        }

        Apartment apartment = new Apartment();
        apartment.setName(name);
        apartment.setFloor(floor);
        apartment.setArea(area);
        apartment.setNumberOfResidents(numberOfResidents);
        apartment.setNumberOfPets(numberOfPets);

        return apartmentRepository.save(apartment);
    }

    @Transactional
    public Apartment update(Long id, String name, Integer floorName, Float area, Integer numberOfResidents, Integer numberOfPets) {
        Apartment apartment = this.findById(id);

        Floor previousFloor = apartment.getFloor();
        Floor floor = floorService.firstOrCreate(apartment.getFloor().getBuilding(), floorName);

        if (!apartment.getName().equals(name) && apartmentRepository.existsByNameAndFloor(name, floor)) {
            throw new CannotUpdateResourceException("Apartment with the given name already exists on the specified floor.");
        }

        apartment.setName(name);
        apartment.setFloor(floor);
        apartment.setArea(area);
        apartment.setNumberOfResidents(numberOfResidents);
        apartment.setNumberOfPets(numberOfPets);

        Apartment updatedApartment = apartmentRepository.save(apartment);

        this.deleteFloorIfEmpty(previousFloor);

        return updatedApartment;
    }

    private void deleteFloorIfEmpty(Floor floor) {
        List<Apartment> apartmentsOnFloor = apartmentRepository.findByFloor(floor);
        if (apartmentsOnFloor.isEmpty()) {
            Building building = floor.getBuilding();
            if (building != null && building.getFloors() != null) {
                building.getFloors().remove(floor);
            }
            floorService.delete(floor);
        }
    }

    public Apartment findById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new CannotRetrieveResourceException("Apartment with the given ID does not exist."));
    }

    public List<Apartment> list(Long buildingId) {
        return apartmentRepository.findByFloorBuildingId(buildingId);
    }
}
