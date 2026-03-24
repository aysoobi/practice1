package module5.pro.serviceImpl;

import lombok.RequiredArgsConstructor;
import module5.pro.exceptions.CountryNotFoundException;
import module5.pro.model.Manufacturer;
import module5.pro.repository.ManufacturerRepo;
import module5.pro.services.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final ManufacturerRepo repo;

    @Override
    public List<Manufacturer> getCountries() {
        List<Manufacturer> list=repo.findAll();
        return list;
    }

    @Override
    public Manufacturer getCountry(Long id) {
        Manufacturer manufacturer=repo.findById(id).orElse(null);
        return manufacturer;

    }

    @Override
    public Manufacturer addCountry(Manufacturer manufacturer) {
        Manufacturer manufacturer1=repo.save(manufacturer);
        return manufacturer;
    }

    @Override
    public Manufacturer updateCountry(Manufacturer manufacturer) throws CountryNotFoundException {
        return repo.findById(manufacturer.getId()).map(c->{
            return repo.save(manufacturer);
        }).orElseThrow(()-> new CountryNotFoundException());
    }

    @Override
    public void deleteCountry(Long id) throws CountryNotFoundException {
        Manufacturer check=getCountry(id);
        if(Objects.isNull(check)){
            throw new CountryNotFoundException();
        }
         repo.deleteById(id);
    }

}
