package com.rootcode.labs.emissions;

import com.rootcode.labs.emissions.controllers.EmissionController;
import com.rootcode.labs.emissions.models.Emission;
import com.rootcode.labs.emissions.repositories.EmissionRepository;
import com.rootcode.labs.emissions.services.EmissionServices;
import com.rootcode.labs.emissions.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class EmissionControllerTest {

    private EmissionController emissionController;

    @Mock
    private EmissionRepository emissionRepository;

    @Mock
    private EmissionServices emissionServices;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emissionController = new EmissionController();
        emissionController.emissionRepository = emissionRepository;
        emissionController.emissionServices = emissionServices;
        emissionController.logger = logger;
    }

    @Test
    void testCreateEmissionRecord_success() throws Exception {
        Emission inputEmission = new Emission();
        Emission createdEmission = new Emission();
        createdEmission.setId(1L);
        List<Emission> responseList = new ArrayList<>();
        responseList.add(createdEmission);
        when(emissionServices.isRecordAcceptable(any(Emission.class))).thenReturn(createdEmission);

        Response response = emissionController.createEmissionRecord(inputEmission);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("Record Created", response.getMessage());
        assertEquals(responseList, response.getData());
    }


    @Test
    void testCreateEmissionRecord_failure() throws Exception {
        Emission inputEmission = new Emission();
        Exception exception = new RuntimeException("Record not created");
        when(emissionServices.isRecordAcceptable(any(Emission.class))).thenThrow(exception);

        Response response = emissionController.createEmissionRecord(inputEmission);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals(new ArrayList<Emission>(), response.getData());
    }
}