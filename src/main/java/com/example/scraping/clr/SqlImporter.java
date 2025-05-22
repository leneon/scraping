// package com.example.scraping.clr;

// import java.io.InputStream;
// import java.nio.charset.StandardCharsets;
// import java.sql.Connection;
// import java.sql.Statement;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Profile;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.jdbc.datasource.init.ScriptUtils;
// import org.springframework.stereotype.Component;

// /**
//  * Classe qui importe le fichier SQL au démarrage de l'application,
//  * sauf en mode "test".
//  */
// @Component
// @Profile("!test") // Ne s'exécute pas lors des tests unitaires
// public class SqlImporter implements CommandLineRunner {

//     @Autowired
//     private DataSource dataSource;

//     @Override
//     public void run(String... args) throws Exception {
//         ClassPathResource resource = new ClassPathResource("db/migration/schema-mysql.sql");

//         if (!resource.exists()) {
//             System.out.println("Fichier SQL introuvable : db/migration/schema-mysql.sql. Import ignoré.");
//             return;
//         }

//        try (Connection conn = dataSource.getConnection();
//             Statement stmt = conn.createStatement()) {

//             var rs = stmt.executeQuery("SHOW TABLES LIKE 'ma_table_importee'");
//             if (!rs.next()) {
//                 ScriptUtils.executeSqlScript(conn, resource);
//                 System.out.println("Script SQL exécuté avec succès.");
//             } else {
//                 System.out.println("Script SQL ignoré (la table existe déjà).");
//             }
//         }

        

//     }

    
// }
