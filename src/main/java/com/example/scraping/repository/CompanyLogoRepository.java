package com.example.scraping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scraping.entity.CompanyLogo;

public interface CompanyLogoRepository extends JpaRepository<CompanyLogo, Long> {

    List<CompanyLogo> findAllByCompanyId(Long companyId);

    Optional<CompanyLogo> findOneByCompanyId(Long companyId);}
    