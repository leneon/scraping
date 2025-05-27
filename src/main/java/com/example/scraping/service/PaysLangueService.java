package com.example.scraping.service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.example.scraping.dto.PaysLangueDto;

@Service
public class PaysLangueService {
public List<PaysLangueDto> getTousPaysLangues() {
    return Arrays.stream(Locale.getISOCountries())
        .map(this::createPaysLangueDtoFromCountryCode)
        .sorted(Comparator.comparing(PaysLangueDto::getPaysLabel))
        .collect(Collectors.toList());
}


    public Optional<PaysLangueDto> getPaysLangueParCode(String codePays) {
    return getTousPaysLangues()
            .stream()
            .filter(p -> p.getPaysCode().equalsIgnoreCase(codePays))
            .findFirst();
}

public PaysLangueDto createPaysLangueDtoFromCountryCode(String countryCode) {
    Locale localePays = new Locale("", countryCode);
    String paysLabel = localePays.getDisplayCountry(Locale.FRENCH);

    // Recherche une langue pour ce pays
    // On cherche dans tous les locales une langue associée au pays donné
    Locale localeLangue = Arrays.stream(Locale.getAvailableLocales())
        .filter(l -> l.getCountry().equalsIgnoreCase(countryCode))
        .findFirst()
        .orElse(new Locale("und")); // und = undetermined

    String langueCode = localeLangue.getLanguage();
    String langueLabel = localeLangue.getDisplayLanguage(Locale.FRENCH);

    return new PaysLangueDto(countryCode, paysLabel, langueCode, langueLabel);
}

}
