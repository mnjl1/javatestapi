package com.mnjl1.javatestapi.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnjl1.javatestapi.service.CompanyService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
            jsonNode = objectMapper.readTree(new URL(Constants.basicUl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //fix code url error 429
        jsonNode.forEach(node->{
            String symbol = node.get("symbol").asText();
            URL url = null;
            try {
                url = constructUrlWithSymbol(symbol);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                JsonNode companyNode = objectMapper
                        .readTree(url);
                System.out.println(companyNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private URL  constructUrlWithSymbol(String symbol) throws MalformedURLException {
        String protocol = "https";
        String host = "sandbox.iexapis.com";
        String file = String.format("/stable/stock/%s/quote?token=Tpk_ee567917a6b640bb8602834c9d30e571", symbol);
        return  new URL(protocol, host, file);
    }
}
