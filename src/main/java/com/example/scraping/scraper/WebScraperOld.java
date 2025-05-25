// package com.example.scraping.scraper;
// import java.io.IOException;
// import java.net.HttpURLConnection;
// import java.net.URL;

// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.jsoup.select.Elements;

// public class WebScraperOld {
//     public static String findWebsite(String baseDomain) {
// String[] extensions = {
//     //  Domaines génériques (gTLDs)
//     ".com",   // Commercial, usage général
//     ".org",   // Organisations, associations
//     ".net",   // Réseaux, entreprises tech
//     ".info",  // Sites informatifs
//     ".biz",   // Business, commerce électronique
//     ".name",  // Usage personnel
//     ".pro",   // Professionnels certifiés
//     ".xyz",   // Générique, moderne et polyvalent

//     //  Domaines nouveaux/génériques (nTLDs)
//     ".tech",   // Technologies, startups
//     ".dev",    // Développeurs, projets tech
//     ".app",    // Applications, mobile ou web
//     ".shop",   // E-commerce, boutiques
//     ".blog",   // Blogs personnels ou pro
//     ".site",   // Site web généraliste
//     ".online", // Présence en ligne en général
//     ".art",    // Artistes, musées
//     ".design", // Design graphique, produits
//     ".cloud",  // Services cloud
//     ".studio", // Agences créatives, médias

//     //  Domaines géographiques (ccTLDs)
//     ".fr",   // France
//     ".us",   // États-Unis
//     ".uk",   // Royaume-Uni
//     ".de",   // Allemagne
//     ".ca",   // Canada
//     ".au",   // Australie
//     ".cn",   // Chine
//     ".jp",   // Japon
//     ".in",   // Inde
//     ".br",   // Brésil
//     ".ma",   // Maroc
//     ".sn",   // Sénégal
//     ".ci",   // Côte d’Ivoire
//     ".be",   // Belgique
//     ".ch",   // Suisse

//     //  Domaines sponsorisés (sTLDs)
//     ".edu",  // Éducation (réservé aux USA)
//     ".gov",  // Gouvernement américain
//     ".mil",  // Militaire américain
//     ".int",  // Organisations internationales
//     ".museum", // Musées
//     ".aero",   // Industrie aéronautique
//     ".coop",   // Coopératives

//     //  Autres extensions modernes ou de marques
//     ".io",   // Très utilisé par les startups tech (origine : Territoire britannique)
//     ".co",   // Alternative à .com, origine Colombie
//     ".tv",   // Médias, streaming (origine : Tuvalu)
//     ".me",   // Personnel, branding (origine : Monténégro)
//     ".ai",   // Intelligence artificielle (origine : Anguilla)
//     ".ly",   // Utilisé en jeux de mots courts, origine : Libye
//     ".to",   // Tonga, souvent utilisé pour des raccourcisseurs de lien
// };

//         for (String ext : extensions) {
//             String fullUrl = "https://www." + baseDomain + ext;
//             if (urlExists(fullUrl)) {
//                 return fullUrl;
//             }
//         }

//         return null; // Aucun site trouvé
//     }

//     private static boolean urlExists(String urlStr) {
//     try {
//         URL url = new URL(urlStr);
//         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//         connection.setRequestMethod("GET"); // plus compatible que HEAD
//         connection.setConnectTimeout(5000);
//         connection.setReadTimeout(5000);
//         int responseCode = connection.getResponseCode();
//         System.out.println("\n\n\n\nResponse code for " + urlStr + ": " + responseCode+"\n\n\n\n");
//         return responseCode >= 200 && responseCode < 400;
//     } catch (Exception e) {
//         return false;
//     }
// }

//     public static String findLogoUrl(String websiteUrl) {
//         if(websiteUrl==null)
//             return null;
            
//         try {
//             Document doc = Jsoup.connect(websiteUrl)
//                                 .userAgent("Mozilla/5.0")
//                                 .get();

//             // 1. Chercher une image avec "logo" dans alt/src/class/id
//             Elements imgs = doc.select("img");
//             for (Element img : imgs) {
//                 String alt = img.attr("alt").toLowerCase();
//                 String src = img.attr("src").toLowerCase();
//                 String cls = img.className().toLowerCase();
//                 String id = img.id().toLowerCase();

//                 if ((alt.contains("logo") || src.contains("logo") || cls.contains("logo") || id.contains("logo"))) {
//                     String absUrl = img.attr("abs:src");
//                     if (absUrl != null && !absUrl.isEmpty()) {
//                         return absUrl;
//                     }
//                 }
//             }

//             // 2. Chercher les balises <link rel="icon"> etc.
//             Elements icons = doc.select("link[rel~=(?i)(icon|shortcut icon|apple-touch-icon)]");
//             for (Element icon : icons) {
//                 String href = icon.attr("abs:href");
//                 if (href != null && !href.isEmpty() &&
//                     (href.endsWith(".ico") || href.endsWith(".png") || href.endsWith(".jpg") || href.endsWith(".svg"))) {
//                     return href;
//                 }
//             }

//             // 3. Dernier recours : favicon par défaut
//             URL url = new URL(websiteUrl);
//             return url.getProtocol() + "://" + url.getHost() + "/favicon.ico";

//         } catch (IOException e) {
//             return null;
//         }
//     }

// }