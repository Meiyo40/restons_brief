package com.simplon.restonsbrief.service;

import com.simplon.restonsbrief.model.entity.Country;
import com.simplon.restonsbrief.model.repository.CountryRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Country> getCountry(String pCode) {
        Optional<Country> country = this.repository.findByCode(pCode);
        Country entity = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(country.isPresent()) {
            entity = country.get();
            status = HttpStatus.OK;
        } else {
            entity = this.badErrorHandling("Aucun pays avec ce code n'a été trouvé.");
        }
        return new ResponseEntity<>(entity, status);
    }

    public ResponseEntity<Country> getCountry(Long id) {
        Optional<Country> country = this.repository.findById(id);
        Country entity = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(country.isPresent()) {
            entity = country.get();
            status = HttpStatus.OK;
        } else {
            entity = this.badErrorHandling("Aucun pays avec cette ID n'a été trouvé.");
        }
        return new ResponseEntity<>(entity, status);
    }

    public ResponseEntity<Country> getCountryByName(String pName) {
        Optional<Country> country = this.repository.findByName(pName);
        Country entity = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(country.isPresent()) {
            entity = country.get();
            status = HttpStatus.OK;
        } else {
            entity = this.badErrorHandling("Aucun pays avec ce nom n'a été trouvé.");
        }
        return new ResponseEntity<>(entity, status);
    }

    public ResponseEntity<List<Country>> getAll() {
        List<Country> countries = this.repository.findAll();
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(countries.size() > 0) {
            status = HttpStatus.OK;
        } else {
            countries.add(
                    this.badErrorHandling("Aucun pays avec ce code n'a été trouvé.")
            );
        }
        return new ResponseEntity<>(countries, status);
    }

    /**
     * Crée l'entité Country si cette dernière n'existe pas. Used by POST method
     * */
    public ResponseEntity<Country> createCountry(Country country) {

        Optional<Country> countryExist = this.repository.findByName(country.getName());

        if(country.getId() != null) {
            country.setId(null);
        }

        if(countryExist.isPresent()) {
            country = this.badErrorHandling("Le pays existe déjà, modifiez-le depuis la liste.");
            return new ResponseEntity<>(country, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(this.persistCountry(country), HttpStatus.CREATED);
    }

    private Country persistCountry(Country country) {
        if(!this.countryValidation(country)) {
            country = this.badErrorHandling("Format invalide, vérifiez les données envoyé.");
        }
        try {
            country = this.repository.save(country);
            return country;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return this.badErrorHandling("Erreur lors de la sauvegarde des données, vérifiez les données envoyé.");
        }
    }

    /**
     * Sauvegarde l'entité Country si le format est correspond à ce qui est attendu. Used by POST and PUT method
     * */
    public ResponseEntity<Country> setCountry(Long id, Country pCountry) {
        Optional<Country> countryExist = this.repository.findById(id);
        Country country = countryExist.isPresent() ? countryExist.get() : null;

        if(pCountry.getName() != null && !pCountry.getName().equals(country.getName())) {
            countryExist = this.repository.findByName(pCountry.getName());
            boolean isSame = countryExist.isPresent() && countryExist.get().getId().equals(country.getId());
            if(isSame || !countryExist.isPresent()) {
                country.setName(pCountry.getName());
            } else {
                country = this.badErrorHandling("Un pays portant ce nom existe déjà.");
                return new ResponseEntity<>(country, HttpStatus.NOT_FOUND);
            }
        }

        if(pCountry.getCode() != null && !pCountry.getCode().equals(country.getCode())) {
            countryExist = this.repository.findByCode(pCountry.getCode());
            boolean isSame = countryExist.isPresent() && countryExist.get().getId().equals(country.getId());
            if(isSame || !countryExist.isPresent()) {
                country.setCode(pCountry.getCode());
            }else {
                country = this.badErrorHandling("Un pays portant ce code existe déjà.");
                return new ResponseEntity<>(country, HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(this.persistCountry(country), HttpStatus.OK);
    }

    public void delete(Country country) {
        this.repository.delete(country);
    }

    public ResponseEntity<Long> delete(Long id) {
        HttpStatus status = HttpStatus.OK;

        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(id, status);
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
