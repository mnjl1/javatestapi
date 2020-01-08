package com.mnjl1.javatestapi.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnjl1.javatestapi.domain.Company;
import com.mnjl1.javatestapi.service.CompanyService;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class ImportFromJsonUrl implements ImportData {

    private final CompanyService companyService;

    public ImportFromJsonUrl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void importData() {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(new URL("https://sandbox.iexapis.com/stable/ref-data/" +
                    "symbols?token=Tpk_ee567917a6b640bb8602834c9d30e571"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        jsonNode.forEach(node->{
            Company company = new Company();
            company.setSymbol(String.valueOf(node.get("symbol")));
            company.setExchange(String.valueOf(node.get("exchange")));
            company.setName(String.valueOf(node.get("name")));
            //company.setDate(String.valueOf(node.get("data", formatter)));
            company.setType(String.valueOf(node.get("type")));
            company.setIexId(String.valueOf(node.get("iexId")));
            company.setRegion(String.valueOf(node.get("region")));
            company.setCurrency(String.valueOf(node.get("currency")));
            company.setEnabled(node.get("isEnabled").asBoolean());

            companyService.save(company);

        });
    }
}
