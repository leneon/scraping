package com.example.scraping.serviceImp;

import com.example.scraping.dto.BankDto;
import com.example.scraping.entity.Bank;
import com.example.scraping.repository.BankRepository;
import com.example.scraping.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Override
    public List<BankDto> findAll() {
        return bankRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BankDto findByBic(String bic) {
        return bankRepository.findById(bic)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public BankDto updateWebsiteAndLogo(String bic, String website, String logoUrl) {
        Optional<Bank> optionalBank = bankRepository.findById(bic);
        if (optionalBank.isPresent()) {
            Bank bank = optionalBank.get();
            bank.setWebsite(website);
            bank.setLogoUrl(logoUrl);
            Bank updated = bankRepository.save(bank);
            return toDto(updated);
        }
        return null;
    }

    private BankDto toDto(Bank bank) {
        return BankDto.builder()
                .bic(bank.getBic())
                .isDeleted(bank.getIsDeleted())
                .lastUpdateByIso9362(bank.getLastUpdateByIso9362() != null ? bank.getLastUpdateByIso9362().toString() : null)
                .recordCreationDateByIso9362(bank.getRecordCreationDateByIso9362() != null ? bank.getRecordCreationDateByIso9362().toString() : null)
                .createdAt(bank.getCreatedAt() != null ? bank.getCreatedAt().toString() : null)
                .updatedAt(bank.getUpdatedAt() != null ? bank.getUpdatedAt().toString() : null)
                .address(bank.getAddress())
                .fullLegalName(bank.getFullLegalName())
                .branchCode(bank.getBranchCode())
                .countryId(bank.getCountryId())
                .institType(bank.getInstitType())
                .bankStatus(bank.getBankStatus() != null ? bank.getBankStatus().name() : null)
                .website(bank.getWebsite())
                .build();
    }
}
