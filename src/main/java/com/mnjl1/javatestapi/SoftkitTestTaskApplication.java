package com.mnjl1.javatestapi;

import com.mnjl1.javatestapi.domain.Company;
import com.mnjl1.javatestapi.service.CompanyService;
import com.mnjl1.javatestapi.util.ImportDataFromJsonUrl;
import com.mnjl1.javatestapi.util.asyncmethod.LookUpCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@SpringBootApplication
@EnableAsync
@EnableRetry
public class SoftkitTestTaskApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SoftkitTestTaskApplication.class, args).close();

    }

    @Autowired
    private  CompanyService companyService;
    @Autowired
    private  LookUpCompany lookUpCompany;


    @Override
    public void run(String... args) throws Exception {

        ImportDataFromJsonUrl importDataFromJsonUrl = new ImportDataFromJsonUrl(companyService,
                lookUpCompany);
        importDataFromJsonUrl.importData();

        List<Company> companies = companyService.findAll();

        companies.stream().limit(20).forEach(System.out::println);
    }

}


