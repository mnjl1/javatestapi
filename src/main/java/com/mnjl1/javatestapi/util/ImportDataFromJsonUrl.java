package com.mnjl1.javatestapi.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnjl1.javatestapi.domain.Company;
import com.mnjl1.javatestapi.service.CompanyService;
import com.mnjl1.javatestapi.util.asyncmethod.LookUpCompany;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ImportDataFromJsonUrl implements ImportData {

    private final CompanyService companyService;
    private final LookUpCompany lookUpCompany;

    public ImportDataFromJsonUrl(CompanyService companyService, LookUpCompany lookUpCompany) {
        this.companyService = companyService;
        this.lookUpCompany = lookUpCompany;
    }
    int count=0;
    @Override
    public void importData() {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Start:" +start);
        JsonNode jsonNode = null;


        try {
            jsonNode = objectMapper.readTree(new URL(Constants.basicUl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String>  links = new ArrayList<>();
        List<CompletableFuture<CompletableFuture<Company>>> completableFutures = new ArrayList<>();

        jsonNode.forEach(node->{
            String symbol = node.get("symbol").asText();
            URL url = null;

            try {
                url = constructUrlWithSymbol(symbol);
                links.add(url.toString());
                completableFutures.add(downLoadWebPage(url.toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                if (url != null) {

                    CompletableFuture<Company> retrievedCompany = lookUpCompany.getCompanyViaUrl(url.toString());

                    Company company = new Company(retrievedCompany.get().getSymbol(),
                            retrievedCompany.get().getCompanyName(),
                            retrievedCompany.get().getVolume(),
                            retrievedCompany.get().getChangePercent());
                    companyService.save(company);


                    System.out.println(count++ +":" +"Doing: " +LocalDateTime.now());
                }

            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        LocalDateTime end = LocalDateTime.now();

        System.out.println("End: " +end);
    }

    private URL  constructUrlWithSymbol(String symbol) throws MalformedURLException {
        String protocol = "https";
        String host = "sandbox.iexapis.com";
        String file = String.format("/stable/stock/%s/quote?token=Tpk_ee567917a6b640bb8602834c9d30e571", symbol);
        return  new URL(protocol, host, file);
    }

    private CompletableFuture<CompletableFuture<Company>> downLoadWebPage(String url) {

        return CompletableFuture.supplyAsync(
                ()->{

                    CompletableFuture<Company> companyViaUrl=null;
                    try {
                        companyViaUrl = lookUpCompany.getCompanyViaUrl(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return companyViaUrl;
                }
        );

    }
}
