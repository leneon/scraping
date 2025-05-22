package com.example.scraping.scraper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static String findWebsite(String baseDomain) {
        String[] extensions = { ".com", ".fr", ".net", ".org", ".io" ,"co"};

        for (String ext : extensions) {
            String fullUrl = "https://www." + baseDomain + ext;
            if (urlExists(fullUrl)) {
                return fullUrl;
            }
        }

        return null; // Aucun site trouvé
    }

    private static boolean urlExists(String urlStr) {
    try {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // plus compatible que HEAD
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int responseCode = connection.getResponseCode();
        System.out.println("\n\n\n\nResponse code for " + urlStr + ": " + responseCode+"\n\n\n\n");
        return responseCode >= 200 && responseCode < 400;
    } catch (Exception e) {
        return false;
    }
}

    public static String findLogoUrl(String websiteUrl) {
        try {
            Document doc = Jsoup.connect(websiteUrl)
                                .userAgent("Mozilla/5.0")
                                .get();

            // 1. Chercher une image avec "logo" dans alt/src/class/id
            Elements imgs = doc.select("img");
            for (Element img : imgs) {
                String alt = img.attr("alt").toLowerCase();
                String src = img.attr("src").toLowerCase();
                String cls = img.className().toLowerCase();
                String id = img.id().toLowerCase();

                if ((alt.contains("logo") || src.contains("logo") || cls.contains("logo") || id.contains("logo"))) {
                    String absUrl = img.attr("abs:src");
                    if (absUrl != null && !absUrl.isEmpty()) {
                        return absUrl;
                    }
                }
            }

            // 2. Chercher les balises <link rel="icon"> etc.
            Elements icons = doc.select("link[rel~=(?i)(icon|shortcut icon|apple-touch-icon)]");
            for (Element icon : icons) {
                String href = icon.attr("abs:href");
                if (href != null && !href.isEmpty() &&
                    (href.endsWith(".ico") || href.endsWith(".png") || href.endsWith(".jpg") || href.endsWith(".svg"))) {
                    return href;
                }
            }

            // 3. Dernier recours : favicon par défaut
            URL url = new URL(websiteUrl);
            return url.getProtocol() + "://" + url.getHost() + "/favicon.ico";

        } catch (IOException e) {
            return null;
        }
    }

}