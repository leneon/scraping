package com.example.scraping.service;

import java.util.List;

import com.example.scraping.dto.HolidayDto;

public interface HolidayService {

    List<HolidayDto> getHolidays(String countryCode, int year);

    List<HolidayDto> getAllHolidays(int year);
}