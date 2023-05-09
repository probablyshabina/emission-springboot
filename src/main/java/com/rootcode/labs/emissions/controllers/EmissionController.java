package com.rootcode.labs.emissions.controllers;

import com.rootcode.labs.emissions.Excceptions.FileNotFound;
import com.rootcode.labs.emissions.Excceptions.RecordIsNotAfter2007;
import com.rootcode.labs.emissions.helpers.CSVReader;
import com.rootcode.labs.emissions.models.Emission;
import com.rootcode.labs.emissions.models.TotalEmissions;
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
    public
    EmissionRepository emissionRepository;
    public EmissionServices emissionServices = new EmissionServices();
    public Logger logger  = LoggerFactory.getLogger(EmissionController.class);

    //Endpoint to get All the records of emission table
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

    //Endpoint to create one emission record into emission table
    @PostMapping
    public Response createEmissionRecord(@RequestBody Emission emission){
        try{
            Emission createdRecord = emissionServices.isRecordAcceptable(emission);
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

    //Endpoint to upload the csv file to read and save the record to the database
    @PostMapping("/upload")
    public Response uploadCSVToDB(@RequestParam("file") MultipartFile file) {
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

    //Endpoint to get the sector which has emitted any gas more than 500 Tonnes
    @GetMapping("/summary")
    public Response getSectorsTotalEmission(@RequestParam("year") int year){
        try{
            List<TotalEmissions> emissions = emissionRepository.emissionGreaterThan500(year);

            if(emissions.size() > 0){
                logger.info("Sectors which has emitted more than 500 Tonnes of any gas in the year " + year + " fetched");
                return new Response(
                        true,
                        HttpStatus.FOUND,
                        HttpStatusCode.valueOf(HttpStatus.FOUND.value()),
                        "Sectors which has emitted more than 500 Tonnes of any gas in the year " + year + " fetched",
                        emissions
                );
            }
            else {
                logger.error("Empty Result Set");
                return new Response(
                        false,
                        HttpStatus.NOT_FOUND,
                        HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()),
                        "Empty Result Set",
                        emissions
                );
            }
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new Response(
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    ex.getMessage(),
                    new ArrayList<>()
            );
        }
    }

    @GetMapping("/max-emission")
    public Response getMaximumEmittedSector(@RequestParam("year") int year){
        try{
            List<TotalEmissions> emissions = emissionRepository.maximumEmittedSector(year);

            if(emissions.size() > 0){
                logger.info("Sector with maximum greenhouse gas emission in the year" + year + " fetched");
                return new Response(
                        true,
                        HttpStatus.FOUND,
                        HttpStatusCode.valueOf(HttpStatus.FOUND.value()),
                        "Sector with maximum greenhouse gas emission in the year " + year + " fetched",
                        emissions
                );
            }
            else{
                logger.error("Empty Result Set");
                return new Response(
                        false,
                        HttpStatus.NOT_FOUND,
                        HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()),
                        "Empty results set ",
                        emissions
                );
            }
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new Response(
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    ex.getMessage(),
                    new ArrayList<>()
            );
        }
    }
}
