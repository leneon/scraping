package com.example.scraping.service;


import com.example.scraping.dto.BankDto;

import java.util.List;

public interface BankService {

    List<BankDto> findAll();

    BankDto findByBic(String bic);

    BankDto updateWebsiteAndLogo(String bic, String website, String logoUrl);
}
