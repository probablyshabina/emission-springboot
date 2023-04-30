package com.rootcode.labs.emissions.helpers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rootcode.labs.emissions.Excceptions.FileIsEmpty;
import com.rootcode.labs.emissions.Excceptions.FileNotFound;
import com.rootcode.labs.emissions.models.Emission;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CSVReader {

    public static List<Emission> readCSVFile (MultipartFile file) throws FileIsEmpty, FileNotFound, IOException {
        if(file != null){
            if(!file.isEmpty()){
                InputStreamReader io = new InputStreamReader(file.getInputStream());
                BufferedReader br = new BufferedReader(io);

                CsvToBean<Emission> csvToBean = new CsvToBeanBuilder(br)
                        .withType(Emission.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Emission> emissionRecordsFromCSV = csvToBean.parse();
                return emissionRecordsFromCSV;
            }
            else
                throw new FileIsEmpty("Uploaded file is empty");
        }
        else
            throw new FileNotFound("uploaded file not found to proceed");
    }
}
