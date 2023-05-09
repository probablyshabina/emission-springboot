package com.rootcode.labs.emissions.utils;

import com.rootcode.labs.emissions.Excceptions.RecordIsNotAfter2007;
import com.rootcode.labs.emissions.Excceptions.ValidateDataException;
import com.rootcode.labs.emissions.models.Emission;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateData {

    public static boolean validateData(Emission emission) throws ValidateDataException, RecordIsNotAfter2007 {

        if(emission.getSector() != null){
            if(!emission.getSector().isEmpty()){
                try{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy");
                    Date date = format.parse(String.valueOf(emission.getYear()));

                    if(emission.getYear() > 2007){
                        if(emission.getStatistic() != null){
                            if(!emission.getStatistic().isEmpty()){

                                if(emission.getStatisticCode() != null){
                                    if(!emission.getStatisticCode().isEmpty()){

                                        if(emission.getValue() >= 0){

                                            if(emission.getUnit() != null){
                                                if(!emission.getUnit().isEmpty()){
                                                    return true;
                                                }
                                                else
                                                    throw new ValidateDataException("Emission Unit field cannot be empty");
                                            }
                                            else
                                                throw new ValidateDataException("Emission Unit field cannot be null");
                                        }
                                        else
                                            throw new ValidateDataException("Emission value must be equal or greater then zero");
                                    }
                                    else
                                        throw new ValidateDataException("Emission Statistic Code field cannot be empty");
                                }
                                else
                                    throw new ValidateDataException("Emission Statistic Code field cannot be null");
                            }
                            else
                                throw new ValidateDataException("Emission Statistic (Gas) field cannot be empty");
                        }
                        else
                            throw new ValidateDataException("Emission Statistic (Gas) field cannot be null");
                    }
                    else
                        throw new RecordIsNotAfter2007("Year mentioned in record is before 2007");

                } catch (Exception ex) {
                    throw new ValidateDataException(ex.getMessage());
                }
            }
            else
                throw new ValidateDataException("Emission Sector field cannot be empty");
        }
        else
            throw new ValidateDataException("Emission Sector field cannot be null");
    }
}
