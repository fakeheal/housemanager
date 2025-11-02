package edu.nbu.repositories;

import edu.nbu.entities.Fee;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.Valid;

import java.time.YearMonth;
import java.util.List;

@Repository
public interface FeeRepository extends CrudRepository<@Valid Fee, Long> {
    @Query("SELECT f FROM Fee f " +
            "JOIN f.apartment a " +
            "JOIN a.floor fl " +
            "WHERE fl.building.id = :buildingId AND f.period = :period")
    List<Fee> findByApartmentFloorBuildingIdAndPeriod(Long buildingId, YearMonth period);

}
