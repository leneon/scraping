package com.example.scraping.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLogoDto {
    private Long id;
    private Long companyId;
    private String websiteUrl;
    private String logoUrl;
    private String path;
    private LocalDateTime downloadedAt;
}
