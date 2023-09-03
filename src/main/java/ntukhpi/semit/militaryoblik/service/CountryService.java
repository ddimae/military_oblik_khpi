package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;

import java.util.List;

public interface CountryService {

    Country createCountry(Country counrty);

    Country getCountryById(Long id);

    List<Country> getAllCountry();

    Country updateCountry(Long id, Country updatedCountry);

    void deleteCountry(Long id);
}
