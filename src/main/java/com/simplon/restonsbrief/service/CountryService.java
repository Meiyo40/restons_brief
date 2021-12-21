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
        return country.isPresent() ? country.get() : this.badErrorHandling("Aucun pays avec ce code n'a été trouvé.");
    }

    public Country getCountry(Long id) {
        Optional<Country> country = this.repository.findById(id);
        return country.isPresent() ? country.get() : this.badErrorHandling("Aucun pays avec cette ID n'a été trouvé.");
    }

    public Country getCountryByName(String pName) {
        Optional<Country> country = this.repository.findByName(pName);
        return country.isPresent() ? country.get() : this.badErrorHandling("Aucun pays avec ce nom n'a été trouvé.");
    }

    public List<Country> getAll() {
        return this.repository.findAll();
    }

    /**
     * Crée l'entité Country si cette dernière n'existe pas.
     * */
    public Country createCountry(Country country) {

        Optional<Country> countryExist = this.repository.findByName(country.getName());

        if(country.getId() != null) {
            country.setId(null);
        }

        if(countryExist.isPresent()) {
            return this.badErrorHandling("Un pays portant ce nom existe déjà.");
        }

        return this.setCountry(country);
    }

    /**
     * Sauvegarde l'entité Country si le format est correspond à ce qui est attendu.
     * */
    public Country setCountry(Country country) {
        if(!this.countryValidation(country)) {
            return this.badErrorHandling("Format invalide, vérifiez les données envoyé.");
        }

        return this.repository.save(country);
    }

    public void delete(Country country) {
        this.repository.delete(country);
    }

    public Long delete(Long id) {
        this.repository.deleteById(id);
        return id;
    }

    /**
     * Contrôle si la country renseigné contient bien un code de 2 char et que le nom n'est pas vide.
     * */
    private boolean countryValidation(Country country) {
        return (country.getCode().length() == 2) && (country.getName().length() > 0);
    }

    /**
     * Permet de retourner un message d'erreur spécifique basé sur le format de l'entité Country (retour en JSON)
     * */
    private Country badErrorHandling(String message) {
        return new Country(null, "ERROR", message);
    }
}
