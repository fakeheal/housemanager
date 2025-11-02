package edu.nbu.repositories;

import edu.nbu.entities.Building;
import edu.nbu.entities.Floor;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.Valid;

import java.util.Optional;

@Repository
public interface FloorRepository extends CrudRepository<@Valid Floor, Long> {
    Optional<Floor> findByBuildingAndName(Building building, Integer name);
}
