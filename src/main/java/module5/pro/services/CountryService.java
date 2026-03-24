package module5.pro.services;

import module5.pro.exceptions.CountryNotFoundException;
import module5.pro.model.Manufacturer;

import java.util.List;

public interface CountryService {
    List<Manufacturer> getCountries();
    Manufacturer getCountry(Long id);
    Manufacturer addCountry(Manufacturer manufacturer);
    Manufacturer updateCountry(Manufacturer manufacturer) throws CountryNotFoundException;
    void deleteCountry(Long id) throws CountryNotFoundException;
}
