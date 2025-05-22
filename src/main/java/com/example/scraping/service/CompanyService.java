package com.example.scraping.service;

import java.util.List;
import java.util.Optional;


import com.example.scraping.dto.CompanyDto;
import com.example.scraping.entity.Company;

public interface CompanyService {
    
    // Créer ou mettre à jour une entreprise
    Company save(CompanyDto dto);

    // Récupérer toutes les entreprises
    List<Company> findAll();

    // Récupérer une entreprise par son ID
    Optional<Company> findById(Long id);

    // Supprimer une entreprise par son ID
    void deleteById(Long id);

    // Mettre à jour une entreprise existante
    Company update(Long id, CompanyDto dto);

    // Rechercher une entreprise par nom
    List<Company> findByName(String name);
}
