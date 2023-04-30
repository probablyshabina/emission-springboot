package com.rootcode.labs.emissions.controllers;

import com.rootcode.labs.emissions.Excceptions.FileNotFound;
import com.rootcode.labs.emissions.Excceptions.RecordIsNotAfter2007;
import com.rootcode.labs.emissions.helpers.CSVReader;
import com.rootcode.labs.emissions.models.Emission;
import com.rootcode.labs.emissions.repositories.EmissionRepository;
import com.rootcode.labs.emissions.services.EmissionServices;
import com.rootcode.labs.emissions.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/emissions")
public class EmissionController {

    @Autowired
    EmissionRepository emissionRepository;

    EmissionServices emissionServices = new EmissionServices();
    Logger logger  = LoggerFactory.getLogger(EmissionController.class);

    @GetMapping("/")
    public String returnIndex(){
        logger.info("Landed on Index page");
        return "index";
    }
    @GetMapping
    public Response getAllEmissionRecords(){
        List<Emission> emissions = (List<Emission>) emissionRepository.findAll();
        if(emissions.size() > 0){
            logger.info("Emission Records fetched");
            return new Response(
                    true,
                    HttpStatus.OK,
                    HttpStatusCode.valueOf(HttpStatus.OK.value()),
                    "Emission Records fetched",
                    emissions
            );
        }
        else{
            logger.error("Emission Records are empty");
            return new Response(
                    false,
                    HttpStatus.NO_CONTENT,
                    HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()),
                    "Emission Records are empty",
                    new ArrayList<Emission>()
            );
        }
    }

    @PostMapping
    public Response createEmissionRecord(@RequestBody Emission emission){
        try{
            Emission validatedRecord = emissionServices.isRecordAcceptable(emission);
            Emission createdRecord = emissionRepository.save(emission);
            List<Emission> responseList = new ArrayList<>();
            responseList.add(createdRecord);

            logger.info("Record Created");
            return new Response(
                    true,
                    HttpStatus.CREATED,
                    HttpStatusCode.valueOf(HttpStatus.CREATED.value()),
                    "Record Created",
                    responseList
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Response(
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    e.getMessage(),
                    new ArrayList<Emission>()
            );
        }
    }

    @PostMapping("/upload")
    public Response uploadCSVToDB(@RequestParam("file") MultipartFile file) throws FileNotFound {
        try{
            List<Emission> emissionRecordsFromCSV = CSVReader.readCSVFile(file);
            List<Emission> emissionRecords = (List<Emission>) emissionRepository.saveAll(emissionRecordsFromCSV);

            if(emissionRecords.size() > 0){
                logger.info("Records uploaded to database successfully");
                return new Response(
                        true,
                        HttpStatus.CREATED,
                        HttpStatusCode.valueOf(HttpStatus.CREATED.value()),
                        "Successfully records uploaded to database",
                        emissionRecords
                );
            }
            else {
                logger.error("Failed to create records");
                return new Response(
                        false,
                        HttpStatus.NO_CONTENT,
                        HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()),
                        "Failed to create records",
                        new ArrayList<Emission>()
                );
            }
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new Response(
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    ex.getMessage(),
                    new ArrayList<Emission>()
            );
        }
    }
}
