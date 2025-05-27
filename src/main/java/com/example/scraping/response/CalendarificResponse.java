package com.example.scraping.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class CalendarificResponse {
    private Meta meta;
    private CalendarificResponseData response; // ✅ C'était une seule instance, pas une liste

    @Data
    public static class Meta {
        private int code;
    }

    @Data
    public static class CalendarificResponseData {
        private List<Holiday> holidays;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Holiday {
        private String name;
        private String description;
        private Country country;
        private Date date;
        private List<String> type;

        @Data
        public static class Country {
            private String id;
            private String name;
        }

        @Data
        public static class Date {
            private String iso;
        }
    }
}
