package com.example.scraping.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scraping.dto.CompanyLogoDto;
import com.example.scraping.entity.Bank;
import com.example.scraping.entity.CompanyLogo;
import com.example.scraping.repository.BankRepository;
import com.example.scraping.repository.CompanyLogoRepository;
import com.example.scraping.service.CompanyLogoService;

@Service
public class CompanyLogoServiceImpl implements CompanyLogoService {

    @Autowired
    private CompanyLogoRepository logoRepository;

    @Autowired
    private BankRepository bankRepository;

    @Override
    public CompanyLogo save(CompanyLogoDto dto) {
        Bank bank = bankRepository.findById(dto.getBankBic())
                .orElseThrow(() -> new RuntimeException("Banque introuvable"));

        // Vérifie s'il existe déjà un logo pour cette banque
        Optional<CompanyLogo> existingLogoOpt = logoRepository.findOneByBankBic(dto.getBankBic());

        CompanyLogo logo = existingLogoOpt.orElse(new CompanyLogo());
        logo.setBankBic(bank.getBic());  
        logo.setWebsiteUrl(dto.getWebsiteUrl());
        logo.setLogoUrl(dto.getLogoUrl());
        logo.setPath(dto.getPath());
        logo.setDownloadedAt(dto.getDownloadedAt());

        return logoRepository.save(logo);
    }

    @Override
    public List<CompanyLogo> findByCompanyId(String companyId) {
        return logoRepository.findAllByBankBic(companyId);
    }

    @Override
    public Optional<CompanyLogo> findOneByCompanyId(String companyId) {
        return logoRepository.findOneByBankBic(companyId);
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
