package com.example.scraping.Utils;


public class AppUtils {
    public static final String BASE_HOLIDAY_API_URL = "https://date.nager.at/api/v3/PublicHolidays";
    public static final String AVAILABLE_COUNTRIES_URL = "https://date.nager.at/api/v3/AvailableCountries";

    public static String buildHolidayApiUrl(String countryCode, int year) {
        return String.format("%s/%d/%s", BASE_HOLIDAY_API_URL, year, countryCode.toUpperCase());
    }
}
