package com.example.scraping.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.scraping.dto.PaysLangueDto;
import com.example.scraping.service.PaysLangueService;

import java.util.List;

@RestController
@RequestMapping("/api/payslangues")
public class PaysLangueController {

    private final PaysLangueService service;

    public PaysLangueController(PaysLangueService service) {
        this.service = service;
    }

    @GetMapping("/tous")
    public List<PaysLangueDto> getTousPaysLangues() {
        return service.getTousPaysLangues();
    }
    @GetMapping("/{codePays}")
    public ResponseEntity<PaysLangueDto> getPaysLangueByCode(@PathVariable String codePays) {
        return service.getPaysLangueParCode(codePays)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
