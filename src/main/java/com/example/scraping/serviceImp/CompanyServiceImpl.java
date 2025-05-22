package com.example.scraping.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scraping.dto.CompanyDto;
import com.example.scraping.entity.Company;
import com.example.scraping.repository.CompanyRepository;
import com.example.scraping.service.CompanyService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company save(CompanyDto dto) {
    // Vérifie si une entreprise avec le même nom existe déjà
    Optional<Company> existingCompany = companyRepository.findByName(dto.getName());
    if (existingCompany.isPresent()) {
        return existingCompany.get(); // Retourne l'entreprise existante
    }

    // Création et sauvegarde si le nom est unique
    Company company = new Company();
    company.setName(dto.getName());
    return companyRepository.save(company);
}
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company update(Long id, CompanyDto dto) {
        Optional<Company> existing = companyRepository.findById(id);
        if (existing.isPresent()) {
            Company company = existing.get();
            company.setName(dto.getName());
            return companyRepository.save(company);
        } else {
            throw new EntityNotFoundException("Aucune compagnie n'existe avec l'id : " + id);
        }
    }

    @Override
    public List<Company> findByName(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }
}
