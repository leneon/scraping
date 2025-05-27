package com.example.scraping.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {

    @Id
    @Column(name = "bic", nullable = false, length = 8)
    private String bic;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "last_update_by_iso9362")
    private LocalDate lastUpdateByIso9362;

    @Column(name = "record_creation_date_by_iso9362")
    private LocalDate recordCreationDateByIso9362;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "address", nullable = false, length = 512)
    private String address;

    @Column(name = "full_legal_name", nullable = false, length = 512)
    private String fullLegalName;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "instit_type")
    private String institType;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_status")
    private BankStatus bankStatus;

    @Column(name = "website", length = 512)
    private String website;

    @Column(name = "logo_url", length = 512)
    private String logoUrl;

    public enum BankStatus {
        ACTIVE,
        CLOSED,
        INACTIVE
    }
}
