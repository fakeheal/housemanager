package edu.nbu.repositories;

import edu.nbu.entities.Apartment;
import edu.nbu.entities.Floor;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.Valid;

import java.util.List;

@Repository
public interface ApartmentRepository extends CrudRepository<@Valid Apartment, Long> {
    boolean existsByNameAndFloor(String name, Floor floor);

    @Query("SELECT a FROM Apartment a JOIN a.floor f WHERE f.building.id = :buildingId")
    List<Apartment> findByFloorBuildingId(Long buildingId);
}
