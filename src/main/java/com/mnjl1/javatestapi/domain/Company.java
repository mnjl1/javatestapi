package com.mnjl1.javatestapi.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String exchange;
    private String name;
    private LocalDate date;
    private String type;
    private String iexId;
    private String region;
    private String currency;
    private boolean isEnabled;

    public Company() {
    }

    public Company(String symbol, String exchange, String name, LocalDate date, String type, String iexId,
                   String region, String currency, boolean isEnabled) {
        this.symbol = symbol;
        this.exchange = exchange;
        this.name = name;
        this.date = date;
        this.type = type;
        this.iexId = iexId;
        this.region = region;
        this.currency = currency;
        this.isEnabled = isEnabled;
    }
}
