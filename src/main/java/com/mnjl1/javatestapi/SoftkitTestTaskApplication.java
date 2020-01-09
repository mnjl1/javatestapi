package com.mnjl1.javatestapi;

import com.mnjl1.javatestapi.service.CompanyService;
import com.mnjl1.javatestapi.util.ImportFromJsonUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoftkitTestTaskApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SoftkitTestTaskApplication.class, args);
    }

    @Autowired
    CompanyService companyService;

    @Override
    public void run(String... args) throws Exception {

        ImportFromJsonUrl importFromJsonUrl = new ImportFromJsonUrl(companyService);
        importFromJsonUrl.importData();
    }
}


