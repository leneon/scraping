package com.example.scraping.service;

import java.util.List;
import java.util.Optional;

import com.example.scraping.dto.CompanyLogoDto;
import com.example.scraping.entity.CompanyLogo;

public interface CompanyLogoService {

    // Sauvegarder un nouveau logo ou mettre à jour s'il existe déjà
    CompanyLogo save(CompanyLogoDto dto);

    // Récupérer tous les logos liés à une entreprise
    List<CompanyLogo> findByCompanyId(String companyId);

    // Récupérer un logo unique pour une entreprise (si un seul est autorisé)
    Optional<CompanyLogo> findOneByCompanyId(String companyId);

    // Mettre à jour un logo existant
    CompanyLogo update(CompanyLogo logo);

    // Supprimer un logo par son ID
    void deleteById(Long id);

    // Récupérer tous les logos
    List<CompanyLogo> findAll();
}

