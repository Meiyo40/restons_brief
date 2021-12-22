package com.simplon.restonsbrief.controller;

import com.simplon.restonsbrief.model.entity.Country;
import com.simplon.restonsbrief.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    private final CountryService service;

    @Autowired
    public CountryController (CountryService pService) {
        this.service = pService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return this.service.getAll();
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        return this.service.getCountry(id);
    }

    @GetMapping("/country/code/{countryCode}")
    public ResponseEntity<Country> getCountry(@PathVariable String countryCode) {
        return this.service.getCountry(countryCode);
    }

    @GetMapping("/country/name/{countryName}")
    public ResponseEntity<Country> getCountryByName(@PathVariable String countryName) {
        return this.service.getCountryByName(countryName);
    }

    @PutMapping("/country/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country pCountry) {
        return this.service.setCountry(id);
    }

    @PostMapping("/country")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return this.service.createCountry(country);
    }

    @DeleteMapping("/country/{id}")
    public ResponseEntity<Long> deleteCountry(@PathVariable Long id) {
        return this.service.delete(id);
    }
}
