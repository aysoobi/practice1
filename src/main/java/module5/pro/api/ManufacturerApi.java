package module5.pro.api;

import lombok.RequiredArgsConstructor;
import module5.pro.model.Manufacturer;
import module5.pro.repository.ManufacturerRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/country")
public class ManufacturerApi {
    private final ManufacturerRepo manufacturerRepo;

    @GetMapping("/")
    public String addManufacturer(){
        return "manufacturer-add";
    }

    @PostMapping("/addcountry")
    public String add(@RequestParam(name="country_name") String name,
                      @RequestParam(name="country_code")String code){
        Manufacturer manufacturer=new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCode(code);
        manufacturerRepo.save(manufacturer);
        return "redirect:/food/main";
    }
}
