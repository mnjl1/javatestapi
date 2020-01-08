package com.mnjl1.javatestapi.service;

import com.mnjl1.javatestapi.domain.Company;

import java.util.List;

public interface CompanyService {

    void save(Company company);

    List<Company> findAll();
}
