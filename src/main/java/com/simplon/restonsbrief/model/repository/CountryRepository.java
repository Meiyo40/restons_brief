package com.simplon.restonsbrief.model.repository;

import com.simplon.restonsbrief.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String countryName);
    Optional<Country> findByCode(String countryCode);
}
