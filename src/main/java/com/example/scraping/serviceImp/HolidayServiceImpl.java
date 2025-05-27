package com.example.scraping.serviceImp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.scraping.Utils.AppUtils;
import com.example.scraping.dto.CountryDto;
import com.example.scraping.dto.HolidayDto;
import com.example.scraping.dto.PaysLangueDto;
import com.example.scraping.response.CalendarificResponse;
import com.example.scraping.service.HolidayService;
import com.example.scraping.service.PaysLangueService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayServiceImpl implements HolidayService {

    private final RestTemplate restTemplate = new RestTemplate();
    
    private final PaysLangueService paysLangueService;
    public HolidayServiceImpl(PaysLangueService paysLangueService) {
        this.paysLangueService = paysLangueService;
    }
    @Override
    public List<HolidayDto> getHolidays(String countryCode, int year) {
        // 1. Vérifier si le pays est supporté par Nager
        List<String> supportedCountries = getAvailableNagerCountries();

        if (supportedCountries.contains(countryCode.toUpperCase())) {
            // 2. Appel à l’API Nager
            String url = AppUtils.buildHolidayApiUrl(countryCode, year);
            HolidayDto[] holidays = restTemplate.getForObject(url, HolidayDto[].class);
            if (holidays != null && holidays.length > 0) {
                return Arrays.asList(holidays);
            }
        }

        // 3. Fallback vers Calendarific
        return getHolidaysFromCalendarific(countryCode, year);
    }

    @Override
    public List<HolidayDto> getAllHolidays(int year) {
        List<HolidayDto> allHolidays = new ArrayList<>();

        // Récupération des pays via PaysLangueService
        List<PaysLangueDto> paysLangues = paysLangueService.getTousPaysLangues();

        for (PaysLangueDto pays : paysLangues) {
            List<HolidayDto> holidays = getHolidays(pays.getPaysCode(), year);

            if (holidays == null || holidays.isEmpty()) {
                holidays = getHolidaysFromCalendarific(pays.getPaysCode(), year);
            }

            if (holidays != null) {
                allHolidays.addAll(holidays);
            }
        }

        return allHolidays;
    }

    private List<HolidayDto> getHolidaysFromCalendarific(String countryCode, int year) {
 String url = "https://calendarific.com/api/v2/holidays"
               + "?api_key=" + AppUtils.API_KEY_HOLIDAH
               + "&country=" + countryCode
               + "&year=" + year;
    try {
        CalendarificResponse response = restTemplate.getForObject(url, CalendarificResponse.class);

        if (response != null && response.getResponse() != null) {
            List<CalendarificResponse.Holiday> holidays = response.getResponse().getHolidays();

            return holidays.stream()
                    .map(h -> new HolidayDto(
                            h.getName(),
                            h.getDescription(),
                            h.getDate() != null ? h.getDate().getIso() : null,
                            h.getCountry() != null ? h.getCountry().getName() : null,
                            (h.getType() != null && !h.getType().isEmpty()) ? h.getType().get(0) : null
                    ))
                    .collect(Collectors.toList());
        }
    } catch (Exception e) {
        System.err.println("Erreur lors de l'appel à Calendarific pour le pays " + countryCode + " : " + e.getMessage());
    }

    return new ArrayList<>();
}


    private List<String> getAvailableNagerCountries() {
        String url = AppUtils.AVAILABLE_COUNTRIES_URL;
        CountryDto[] countries = restTemplate.getForObject(url, CountryDto[].class);

        if (countries == null) return List.of();

        List<String> countryCodes = new ArrayList<>();
        for (CountryDto country : countries) {
            countryCodes.add(country.getCountryCode().toUpperCase());
        }
        return countryCodes;
    }

}
