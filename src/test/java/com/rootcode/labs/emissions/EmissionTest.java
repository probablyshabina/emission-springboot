package com.rootcode.labs.emissions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rootcode.labs.emissions.Excceptions.RecordIsNotAfter2007;
import com.rootcode.labs.emissions.Excceptions.ValidateDataException;
import com.rootcode.labs.emissions.controllers.EmissionController;
import com.rootcode.labs.emissions.models.Emission;
import com.rootcode.labs.emissions.repositories.EmissionRepository;
import com.rootcode.labs.emissions.services.EmissionServices;
import com.rootcode.labs.emissions.utils.ValidateData;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmissionTest {


    @Test
    public void testValidateData_EmptySector() {
        Emission emission = new Emission(1, "", 2010, "code", "statistic", "unit", 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_NullSector() {
        Emission emission = new Emission(1, null, 2010, "code", "statistic", "unit", 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_EmptyStatistic() {
        Emission emission = new Emission(1, "sector", 2010, "code", "", "unit", 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_NullStatistic() {
        Emission emission = new Emission(1, "sector", 2010, "code", null, "unit", 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_EmptyStatisticCode() {
        Emission emission = new Emission(1, "sector", 2010, "", "statistic", "unit", 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_NullStatisticCode() {
        Emission emission = new Emission(1, "sector", 2010, null, "statistic", "unit", 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_NegativeValue() {
        Emission emission = new Emission(1, "sector", 2010, "code", "statistic", "unit", -100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_NullUnit() {
        Emission emission = new Emission(1, "sector", 2010, "code", "statistic", null, 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_EmptyUnit() {
        Emission emission = new Emission(1, "sector", 2010, "code", "statistic", "", 100);
        assertThrows(ValidateDataException.class, () -> ValidateData.validateData(emission));
    }

    @Test
    public void testValidateData_SuccessfulValidation() throws ValidateDataException, RecordIsNotAfter2007 {
        Emission emission = new Emission(1, "sector", 2010, "code", "statistic", "kg", 100);
        boolean result = ValidateData.validateData(emission);
        assertEquals(true, result);
    }

    @Test
    public void testIsRecordAcceptable_YearBefore2007() {
        Emission emission = new Emission(1, "sector", 2000, "code", "statistic", "kg", 100);
        assertThrows(RecordIsNotAfter2007.class, () -> ValidateData.validateData(emission));
    }

}

