package com.example.scraping.clr;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class SqlImporter {

    private final DataSource dataSource;

    public SqlImporter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @jakarta.annotation.PostConstruct
    public void run() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            // Vérifie si la table batch_job_instance existe déjà
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'batch_job_instance'");

            if (!rs.next()) {
                // Table n'existe pas, donc on exécute le script SQL
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/migration/schema-mysql.sql"));

                System.out.println(" Script SQL exécuté avec succès.");
            } else {
                // Table existe déjà, donc on ignore le script
                System.out.println(" Script SQL ignoré car la table 'batch_job_instance' existe déjà.");
            }
            
            ResultSet bnks = stmt.executeQuery("SHOW TABLES LIKE 'bank'");
            if (!bnks.next()) {
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/migration/banks_dump.sql"));
                System.out.println(" Script SQL exécuté avec succès.");
            } else {
                System.out.println(" Script SQL ignoré car la table 'batch_job_instance' existe déjà.");
            }


        } catch (Exception e) {
            System.err.println(" Erreur lors de l'exécution du script SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
