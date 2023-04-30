package com.rootcode.labs.emissions.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TotalEmissions {
    private String sector;
    private int year;
    private String statistic_code;
    private double total_emission;

    TotalEmissions(String sector, int year, double total_emission){
        this.sector = sector;
        this.year = year;
        this.total_emission = total_emission;
    }
}
