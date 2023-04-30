package com.rootcode.labs.emissions.repositories;

import com.rootcode.labs.emissions.models.Emission;
import org.springframework.data.repository.CrudRepository;

public interface EmissionRepository extends CrudRepository<Emission, Long> {

}
