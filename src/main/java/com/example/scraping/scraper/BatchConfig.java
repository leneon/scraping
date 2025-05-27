package com.example.scraping.scraper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.scraping.dto.CompanyLogoDto;
import com.example.scraping.entity.Bank;
import com.example.scraping.entity.CompanyLogo;
import com.example.scraping.repository.BankRepository;
import com.example.scraping.service.CompanyLogoService;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job scrapeBankJob(Step scrapeBankStep) {
        return new JobBuilder("scrapeBankJob", jobRepository)
                .start(scrapeBankStep)
                .build();
    }

    @Bean
    public Step scrapeBankStep(Tasklet scrapeBankTasklet) {
        return new StepBuilder("scrapeBankStep", jobRepository)
                .tasklet(scrapeBankTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet scrapeBankTasklet(BankRepository bankRepo, CompanyLogoService logoService) {
        return (contribution, chunkContext) -> {
            List<com.example.scraping.entity.Bank> banks = bankRepo.findFiveBanks();

            for (Bank bank : banks) {
                String name = bank.getFullLegalName();
                String website = WebScraper.findWebsite(name);
                String logoUrl = WebScraper.findLogoUrl(website);

                if (logoUrl == null || logoUrl.isBlank()) {
                    continue;
                }

                // Télécharger et stocker localement
                // String localPath = WebScraper.downloadLogo(logoUrl, name);

                Optional<CompanyLogo> existingLogoOpt = logoService.findOneByCompanyId(bank.getBic());

                if (existingLogoOpt.isPresent()) {
                    CompanyLogo existingLogo = existingLogoOpt.get();
                    existingLogo.setWebsiteUrl(website);
                    existingLogo.setLogoUrl(logoUrl);
                    //existingLogo.setPath(localPath);
                    existingLogo.setDownloadedAt(LocalDateTime.now());
                    logoService.update(existingLogo);
                } else {
                    CompanyLogoDto dto = new CompanyLogoDto();
                    dto.setBankBic(bank.getBic());
                    dto.setWebsiteUrl(website);
                    dto.setLogoUrl(logoUrl);
                    //dto.setPath(localPath);
                    dto.setDownloadedAt(LocalDateTime.now());
                    logoService.save(dto);
                }
            }

            return RepeatStatus.FINISHED;
        };
    }
}
