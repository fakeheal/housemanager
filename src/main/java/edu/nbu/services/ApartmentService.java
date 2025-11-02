package edu.nbu.services;

import edu.nbu.entities.Apartment;
import edu.nbu.entities.Building;
import edu.nbu.entities.Floor;
import edu.nbu.exceptions.crud.CannotCreateResourceException;
import edu.nbu.repositories.ApartmentRepository;
import jakarta.inject.Inject;

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


    public List<Apartment> list(Long buildingId) {
        return apartmentRepository.findByFloorBuildingId(buildingId);
    }
}
