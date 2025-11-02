package edu.nbu.services;

import edu.nbu.entities.Building;
import edu.nbu.entities.Floor;
import edu.nbu.repositories.FloorRepository;
import jakarta.inject.Inject;

public class FloorService {
    @Inject
    FloorRepository floorRepository;


    public Floor firstOrCreate(Building building, Integer name) {
        return floorRepository.findByBuildingAndName(building, name)
                .orElseGet(() -> {
                    Floor floor = new Floor();
                    floor.setBuilding(building);
                    floor.setName(name);
                    return floorRepository.save(floor);
                });
    }
}
