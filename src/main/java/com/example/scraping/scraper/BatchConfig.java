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
import com.example.scraping.entity.Company;
import com.example.scraping.entity.CompanyLogo;
import com.example.scraping.repository.CompanyRepository;
import com.example.scraping.service.CompanyLogoService;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job scrapeCompanyJob(Step scrapeStep) {
        return new JobBuilder("scrapeCompanyJob", jobRepository)
                .start(scrapeStep)
                .build();
    }

    @Bean
    public Step scrapeStep(Tasklet scrapeCompanyTasklet) {
        return new StepBuilder("scrapeStep", jobRepository)
                .tasklet(scrapeCompanyTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet scrapeCompanyTasklet(CompanyRepository companyRepo, CompanyLogoService logoService) {
        return (contribution, chunkContext) -> {
            List<Company> companies = companyRepo.findAll();

            for (Company company : companies) {
                String website = WebScraper.findWebsite(company.getName());
                String logoUrl = WebScraper.findLogoUrl(website);
                if (logoUrl == null) {
                    continue; // Skip if logo URL is not found
                }
                Optional<CompanyLogo> existingLogoOpt = logoService.findOneByCompanyId(company.getId());

                if (existingLogoOpt.isPresent()) {
                    CompanyLogo existingLogo = existingLogoOpt.get();
                    existingLogo.setWebsiteUrl(website);
                    existingLogo.setLogoUrl(logoUrl);
                    existingLogo.setDownloadedAt(LocalDateTime.now());
                    logoService.update(existingLogo);
                } else {
                    CompanyLogoDto dto = new CompanyLogoDto();
                    dto.setCompanyId(company.getId());
                    dto.setWebsiteUrl(website);
                    dto.setLogoUrl(logoUrl);
                    dto.setDownloadedAt(LocalDateTime.now());
                    logoService.save(dto);
                }
            }

            return RepeatStatus.FINISHED;
        };
    }
}
