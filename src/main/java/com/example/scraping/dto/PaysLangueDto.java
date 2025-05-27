package com.example.scraping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaysLangueDto {
     private String paysCode;      // ex: FR
    private String paysLabel;     // ex: France
    private String langueCode;    // ex: fr
    private String langueLabel;   
}
