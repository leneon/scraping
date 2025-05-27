package com.example.scraping.scraper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.example.scraping.Utils.AppUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebScraper {

    /**
     * Recherche du site officiel d'une banque avec SerpAPI
     */
    public static String findWebsite(String bankName) {
        try {
            String encodedQuery = URLEncoder.encode(bankName + " site officiel", StandardCharsets.UTF_8);
            String apiUrl = "https://serpapi.com/search.json?q=" + encodedQuery + "&api_key=" + AppUtils.API_KEY_SEARCH;

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode response = mapper.readTree(connection.getInputStream());
            JsonNode results = response.path("organic_results");

            if (results.isArray() && results.size() > 0) {
                return results.get(0).path("link").asText();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'appel à SerpAPI : " + e.getMessage());
        }
        return null;
    }

    /**
     * Recherche de l'URL du logo depuis un site
     */
    public static String findLogoUrl(String websiteUrl) {
        if (websiteUrl == null) return null;

        try {
            Document doc = Jsoup.connect(websiteUrl)
                                .userAgent("Mozilla/5.0")
                                .timeout(8000)
                                .get();

            for (Element img : doc.select("img")) {
                String alt = img.attr("alt").toLowerCase();
                String src = img.attr("src").toLowerCase();
                String itemprop = img.attr("itemprop").toLowerCase();
                String cls = img.className().toLowerCase();
                String id = img.id().toLowerCase();

                if (alt.contains("logo") || itemprop.contains("logo") || src.contains("logo") || cls.contains("logo") || id.contains("logo")) {
                    String absUrl = img.absUrl("src");
                    if (absUrl != null && !absUrl.isEmpty()) {
                        return absUrl;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Téléchargement du logo sur disque
     */
    public static String downloadLogo(String imageUrl, String bankName) throws IOException {
    String extension = "png"; // par défaut
    int lastDot = imageUrl.lastIndexOf('.');
    if (lastDot >= 0) {
        String extCandidate = imageUrl.substring(lastDot + 1).split("\\?")[0].toLowerCase();
        if (extCandidate.matches("png|jpg|jpeg|gif|bmp|svg")) {
            extension = extCandidate.equals("jpeg") ? "jpg" : extCandidate;
        }
    }

    String sanitizedName = bankName.replaceAll("[^a-zA-Z0-9]", "_");
    String folderPath = "logos/";
    Files.createDirectories(Paths.get(folderPath));
    File outputFile = new File(folderPath + sanitizedName + "." + extension);

    // Cas SVG → juste enregistrer le fichier
    if (extension.equals("svg")) {
        try (var in = new URL(imageUrl).openStream()) {
            Files.copy(in, outputFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return outputFile.getAbsolutePath();
    }

    // Cas image raster classique
    BufferedImage image = ImageIO.read(new URL(imageUrl));
    if (image == null) {
        throw new IOException("Image invalide ou format non pris en charge : " + imageUrl);
    }

    ImageIO.write(image, extension, outputFile);
    return outputFile.getAbsolutePath();
}

}
