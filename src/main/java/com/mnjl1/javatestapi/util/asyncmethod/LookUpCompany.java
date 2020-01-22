package com.mnjl1.javatestapi.util.asyncmethod;

import com.mnjl1.javatestapi.domain.Company;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class LookUpCompany {

    private final RestTemplate restTemplate;

    public LookUpCompany(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async("executor")
    @Retryable(value = {HttpClientErrorException.class}, maxAttempts = 2, backoff = @Backoff(delay = 100L))
    public  CompletableFuture<Company> getCompanyViaUrl(String url)
            throws IOException, InterruptedException {

        Company company = restTemplate.getForObject(url, Company.class);
        return CompletableFuture.completedFuture(company);

    }
}
