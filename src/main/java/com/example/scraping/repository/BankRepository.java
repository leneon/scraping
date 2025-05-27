package com.example.scraping.repository;


import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.scraping.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, String> {
    Bank findByFullLegalName(String fullLegalName);

    Stream<Bank> findByBic(String bic);

    @Query(value = "SELECT * FROM bank LIMIT 10", nativeQuery = true)
    List<Bank> findFiveBanks();


}
    