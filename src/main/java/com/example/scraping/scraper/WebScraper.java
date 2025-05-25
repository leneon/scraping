package com.example.scraping.scraper;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static String findWebsite(String baseDomain) {
String[] extensions = {
    //  Domaines génériques (gTLDs)
    ".com",   // Commercial, usage général
    ".org",   // Organisations, associations
    ".net",   // Réseaux, entreprises tech
    ".info",  // Sites informatifs
    ".biz",   // Business, commerce électronique
    ".name",  // Usage personnel
    ".pro",   // Professionnels certifiés
    ".xyz",   // Générique, moderne et polyvalent

    //  Domaines nouveaux/génériques (nTLDs)
    ".tech",   // Technologies, startups
    ".dev",    // Développeurs, projets tech
    ".app",    // Applications, mobile ou web
    ".shop",   // E-commerce, boutiques
    ".blog",   // Blogs personnels ou pro
    ".site",   // Site web généraliste
    ".online", // Présence en ligne en général
    ".art",    // Artistes, musées
    ".design", // Design graphique, produits
    ".cloud",  // Services cloud
    ".studio", // Agences créatives, médias

    //  Domaines géographiques (ccTLDs)
    ".fr",   // France
    ".us",   // États-Unis
    ".uk",   // Royaume-Uni
    ".de",   // Allemagne
    ".ca",   // Canada
    ".au",   // Australie
    ".cn",   // Chine
    ".jp",   // Japon
    ".in",   // Inde
    ".br",   // Brésil
    ".ma",   // Maroc
    ".sn",   // Sénégal
    ".ci",   // Côte d’Ivoire
    ".be",   // Belgique
    ".ch",   // Suisse

    //  Domaines sponsorisés (sTLDs)
    ".edu",  // Éducation (réservé aux USA)
    ".gov",  // Gouvernement américain
    ".mil",  // Militaire américain
    ".int",  // Organisations internationales
    ".museum", // Musées
    ".aero",   // Industrie aéronautique
    ".coop",   // Coopératives

    //  Autres extensions modernes ou de marques
    ".io",   // Très utilisé par les startups tech (origine : Territoire britannique)
    ".co",   // Alternative à .com, origine Colombie
    ".tv",   // Médias, streaming (origine : Tuvalu)
    ".me",   // Personnel, branding (origine : Monténégro)
    ".ai",   // Intelligence artificielle (origine : Anguilla)
    ".ly",   // Utilisé en jeux de mots courts, origine : Libye
    ".to",   // Tonga, souvent utilisé pour des raccourcisseurs de lien
};

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
    if (websiteUrl == null) return null;

    try {
        Document doc = Jsoup.connect(websiteUrl)
                            .userAgent("Mozilla/5.0")
                            .get();

        // Chercher uniquement les images avec "logo" dans alt, src, class ou id
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

    return null; // Aucun logo trouvé
}

    public static String downloadLogo(String imageUrl, String companyName) throws IOException {
    // Ignorer les formats SVG et ICO
    if (imageUrl.endsWith(".svg") || imageUrl.contains(".svg?") || imageUrl.endsWith(".ico")) {
        System.out.println("Image ignorée (format non supporté) : " + imageUrl);
        return null;
    }

    URL url = new URL(imageUrl);
    BufferedImage image = ImageIO.read(url);
    if (image == null) {
        throw new IOException("L'image est nulle. L'URL ne pointe probablement pas vers une image valide : " + imageUrl);
    }

    // Déduire l'extension du fichier
    String extension = "png"; // défaut
    int lastDotIndex = imageUrl.lastIndexOf('.');
    if (lastDotIndex >= 0) {
        String extCandidate = imageUrl.substring(lastDotIndex + 1).split("\\?")[0].toLowerCase();
        if (extCandidate.matches("png|jpg|jpeg|gif|bmp")) {
            extension = extCandidate.equals("jpeg") ? "jpg" : extCandidate;
        }
    }

    // Créer un nom de fichier sécurisé
    String sanitizedName = companyName.replaceAll("[^a-zA-Z0-9]", "_");
    String folderPath = "logos/";
    Files.createDirectories(Paths.get(folderPath));
    File outputFile = new File(folderPath + sanitizedName + "." + extension);

    ImageIO.write(image, extension, outputFile);
    return outputFile.getAbsolutePath();
}

}