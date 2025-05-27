package com.example.scraping.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDto {
    private String date;
    private String localName;
    private String name;
    private String countryCode;
    private boolean fixed;
    private boolean global;
    private String description;
    private String translatedName;
    private String translatedDescription;
    private String type;


    public HolidayDto(String name, String description, String date, String country, String type) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.countryCode = country;
        this.type = type;
    }
}

