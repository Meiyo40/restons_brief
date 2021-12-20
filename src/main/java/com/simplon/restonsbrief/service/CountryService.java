package com.simplon.restonsbrief.service;

import com.simplon.restonsbrief.model.entity.Country;
import com.simplon.restonsbrief.model.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository pRepository) {
        this.repository = pRepository;
    }

    public Country getCountry(String pCode) {
        Optional<Country> country = this.repository.findByCode(pCode);
        return country.isPresent() ? country.get() : null;
    }

    public Country getCountry(Long id) {
        Optional<Country> country = this.repository.findById(id);
        return country.isPresent() ? country.get() : null;
    }

    public Country getCountryByName(String pName) {
        Optional<Country> country = this.repository.findByName(pName);
        return country.isPresent() ? country.get() : null;
    }

    public List<Country> getAll() {
        List<Country> countries = this.repository.findAll();
        return countries;
    }

    public Country setCountry(Country country) {
        return this.repository.save(country);
    }

    public void delete(Country country) {
        this.repository.delete(country);
    }

    public Long delete(Long id) {
        this.repository.deleteById(id);
        return id;
    }
}
