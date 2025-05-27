package com.example.scraping.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDto {

    private String bic;
    private Boolean isDeleted;
    private String lastUpdateByIso9362;         // format "yyyy-MM-dd"
    private String recordCreationDateByIso9362;  // format "yyyy-MM-dd"
    private String createdAt;                    // format "yyyy-MM-dd'T'HH:mm:ss"
    private String updatedAt;                    // idem
    private String address;
    private String fullLegalName;
    private String branchCode;
    private String countryId;
    private String institType;
    private String bankStatus; // String ici pour simplicité (peut aussi être enum)
    private String website;
}
