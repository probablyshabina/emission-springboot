package com.rootcode.labs.emissions.services;

import com.rootcode.labs.emissions.Excceptions.RecordIsNotAfter2007;
import com.rootcode.labs.emissions.Excceptions.ValidateDataException;
import com.rootcode.labs.emissions.models.Emission;
import com.rootcode.labs.emissions.repositories.EmissionRepository;
import com.rootcode.labs.emissions.utils.ValidateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmissionServices {
    @Autowired
    EmissionRepository emissionRepository;

    public Emission isRecordAcceptable(Emission emission) throws RecordIsNotAfter2007, ValidateDataException {
        if(ValidateData.validateData(emission)){
            if(emission.getUnit().toLowerCase().contains("kilo")
                    || emission.getUnit().toLowerCase().contains("kg")) {
                emission.setValue(emission.getValue()/1000);
            }
            else
                emission.setUnit("000 Tonnes");
        }

        emission = emissionRepository.save(emission);
        return emission;
    }
}
