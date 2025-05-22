package com.example.scraping.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.scraping.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByNameContainingIgnoreCase(String name);

    Optional<Company> findByName(String name);
}
    