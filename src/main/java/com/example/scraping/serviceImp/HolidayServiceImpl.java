package com.example.scraping.serviceImp;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.scraping.Utils.AppUtils;
import com.example.scraping.dto.CountryDto;
import com.example.scraping.dto.HolidayDto;
import com.example.scraping.service.HolidayService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<HolidayDto> getHolidays(String countryCode, int year) {
        String url = AppUtils.buildHolidayApiUrl(countryCode, year);
        HolidayDto[] holidays = restTemplate.getForObject(url, HolidayDto[].class);
        return holidays != null ? Arrays.asList(holidays) : new ArrayList<>();
    }

    @Override
    public List<HolidayDto> getAllHolidays(int year) {
        List<HolidayDto> allHolidays = new ArrayList<>();

        // Récupération des pays disponibles
        String countriesUrl = AppUtils.AVAILABLE_COUNTRIES_URL;
        CountryDto[] countries = restTemplate.getForObject(countriesUrl, CountryDto[].class);

        if (countries != null) {
            for (CountryDto country : countries) {
                List<HolidayDto> holidays = getHolidays(country.getCountryCode(), year);
                allHolidays.addAll(holidays);
            }
        }

        return allHolidays;
    }
}
