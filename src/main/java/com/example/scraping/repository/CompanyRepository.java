package com.example.scraping.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.scraping.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByNameContainingIgnoreCase(String name);
}
    