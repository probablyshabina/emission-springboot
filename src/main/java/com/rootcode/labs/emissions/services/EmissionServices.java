package com.rootcode.labs.emissions.services;

import com.rootcode.labs.emissions.Excceptions.RecordIsNotAfter2007;
import com.rootcode.labs.emissions.models.Emission;

import java.util.Date;
import java.util.List;

public class EmissionServices {

    public Emission isRecordAcceptable(Emission emission) throws RecordIsNotAfter2007{
        if(emission.getYear() > 2007){
            if(emission.getUnit() != null){
                if(!emission.getUnit().isEmpty()){
                    if(emission.getUnit().toLowerCase().contains("kilo") ||
                            emission.getUnit().toLowerCase().contains("kg")){
                        emission.setValue(
                                emission.getValue()/1000
                        );
                    }
                    else
                        emission.setUnit("000 Tonnes");
                }
            }
            emission.setUnit("000 Tonnes");
        }
        else
            throw new RecordIsNotAfter2007("Year mentioned in record is before 2007");

        return emission;
    }
}
