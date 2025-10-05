package edu.nbu.repositories;

import edu.nbu.entities.Building;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.Valid;

@Repository
public interface BuildingRepository extends CrudRepository<@Valid Building, Long> {
}
