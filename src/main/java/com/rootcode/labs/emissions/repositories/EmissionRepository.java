package com.rootcode.labs.emissions.repositories;

import com.rootcode.labs.emissions.models.Emission;
import com.rootcode.labs.emissions.models.TotalEmissions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmissionRepository extends CrudRepository<Emission, Long> {

    @Query("SELECT new com.rootcode.labs.emissions.models.TotalEmissions(e.sector, e.year,  e.statisticCode, sum(e.value))" +
            "from Emission e where e.year = :year group by e.statisticCode, e.sector , e.year " +
            "having sum(e.value) > 500")
    List<TotalEmissions> emissionGreaterThan500(@Param("year") int year);


    @Query("select new com.rootcode.labs.emissions.models.TotalEmissions(e.sector, e.year, sum(e.value)) " +
            "from Emission e  where e.year = :year group by  e.sector , e.year order by  sum(e.value) DESC LIMIT 1")
    List<TotalEmissions> maximumEmittedSector(@Param("year") int year);

}
