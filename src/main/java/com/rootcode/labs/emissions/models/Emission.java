package com.rootcode.labs.emissions.models;

import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.opencsv.bean.CsvBindByName;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "emission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Emission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CsvBindByName(column = "Sector")
    private String sector;
    @CsvBindByName(column = "Year")
    private int year ;
    @CsvBindByName(column = "STATISTIC CODE")
    private String statisticCode;

    @CsvBindByName(column = "Statistic")
    private String statistic;
    @CsvBindByName(column = "UNIT")
    private String unit;
    @CsvBindByName(column = "VALUE")
    private double value;
}
