package com.simplon.restonsbrief.controller;

import com.simplon.restonsbrief.model.entity.Country;
import com.simplon.restonsbrief.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(this.service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.getCountry(id), HttpStatus.OK);
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Country> getCountry(@PathVariable String countryCode) {
        return new ResponseEntity<>(this.service.getCountry(countryCode), HttpStatus.OK);
    }

    @GetMapping("/name/{countryName}")
    public ResponseEntity<Country> getCountryByName(@PathVariable String countryName) {
        return new ResponseEntity<>(this.service.getCountry(countryName), HttpStatus.OK);
    }

    @PutMapping("/country/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country pCountry) {
        Country country = this.service.getCountry(id);
        country.setName(pCountry.getName());
        country.setCode(pCountry.getCode());
        return new ResponseEntity<>(this.service.setCountry(country), HttpStatus.OK);
    }

    @PostMapping("/country")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return new ResponseEntity<>(this.service.setCountry(country), HttpStatus.CREATED);
    }

    @DeleteMapping("/country/{id}")
    public ResponseEntity<Long> deleteCountry(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.delete(id), HttpStatus.OK);
    }
}
