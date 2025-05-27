package com.example.scraping.controller;

import org.springframework.web.bind.annotation.*;

import com.example.scraping.dto.HolidayDto;
import com.example.scraping.service.HolidayService;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/all/{year}")
    public List<HolidayDto> getAllHolidays(@PathVariable int year) {
        return holidayService.getAllHolidays(year);
    }


    @GetMapping("/{countryCode}/{year}")
    public List<HolidayDto> getHolidays(@PathVariable int  year, @PathVariable String countryCode) {
        return holidayService.getHolidays(countryCode, year);
    }

    @GetMapping
    public List<HolidayDto> getHolidays(@RequestParam String countryCode, @RequestParam int year) {
        return holidayService.getHolidays(countryCode, year);
    }


}

