package com.example.scraping.Utils;


public class AppUtils {
    public static final String BASE_HOLIDAY_API_URL = "https://date.nager.at/api/v3/PublicHolidays";
    public static final String AVAILABLE_COUNTRIES_URL = "https://date.nager.at/api/v3/AvailableCountries";

    public static String buildHolidayApiUrl(String countryCode, int year) {
        return String.format("%s/%d/%s", BASE_HOLIDAY_API_URL, year, countryCode.toUpperCase());
    }

    public static final String HOLIDAYS_API = "https://calendarific.com/api/v2/holidays";
    public static final String TRANSLATE_API = "https://libretranslate.com/translate";
    public static final String API_KEY_SEARCH= "fd93f693d7b5ebf3a1ac79f408bb96b66d2f77e6f88c3ac832b4c0e7eda6cfcb";

    public static final String API_KEY_HOLIDAH = "OG4Z5J4uMicDMUIjEd8Q9q2PmnOTHMjr"; // à configurer
}
