package com.example.scraping.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scraping.dto.CompanyLogoDto;
import com.example.scraping.entity.Company;
import com.example.scraping.entity.CompanyLogo;
import com.example.scraping.repository.CompanyLogoRepository;
import com.example.scraping.repository.CompanyRepository;
import com.example.scraping.service.CompanyLogoService;
@Service
public class CompanyLogoServiceImpl implements CompanyLogoService {

    @Autowired
    private CompanyLogoRepository logoRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyLogo save(CompanyLogoDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Compagnie introuvable"));

        // Vérifie s'il existe déjà un logo pour cette entreprise
        Optional<CompanyLogo> existingLogoOpt = logoRepository.findOneByCompanyId(dto.getCompanyId());

        CompanyLogo logo = existingLogoOpt.orElse(new CompanyLogo());
        logo.setCompany(company);
        logo.setWebsiteUrl(dto.getWebsiteUrl());
        logo.setLogoUrl(dto.getLogoUrl());
        logo.setDownloadedAt(dto.getDownloadedAt());

        return logoRepository.save(logo);
    }

    @Override
    public List<CompanyLogo> findByCompanyId(Long companyId) {
        return logoRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Optional<CompanyLogo> findOneByCompanyId(Long companyId) {
        return logoRepository.findOneByCompanyId(companyId);
    }

    @Override
    public CompanyLogo update(CompanyLogo logo) {
        if (!logoRepository.existsById(logo.getId())) {
            throw new RuntimeException("Logo non trouvé pour la mise à jour");
        }
        return logoRepository.save(logo);
    }

    @Override
    public void deleteById(Long id) {
        if (!logoRepository.existsById(id)) {
            throw new RuntimeException("Logo non trouvé");
        }
        logoRepository.deleteById(id);
    }

    @Override
    public List<CompanyLogo> findAll() {
        return logoRepository.findAll();
    }
}
