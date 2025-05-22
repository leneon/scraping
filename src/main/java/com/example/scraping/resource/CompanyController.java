package com.example.scraping.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.scraping.dto.CompanyDto;
import com.example.scraping.entity.Company;
import com.example.scraping.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> create(@RequestBody CompanyDto dto) {
        Company created = companyService.save(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Récupérer toutes les entreprises
    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    // Récupérer une entreprise par ID
    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable Long id) {
        return companyService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", null));
    }

    // Mettre à jour une entreprise
    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody CompanyDto dto) {
        Company updated = companyService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Supprimer une entreprise
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Rechercher par nom (ex : /api/companies/search?name=tech)
    @GetMapping("/search")
    public ResponseEntity<List<Company>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(companyService.findByName(name));
    }
}
