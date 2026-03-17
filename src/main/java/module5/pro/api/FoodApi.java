package module5.pro.api;

import lombok.RequiredArgsConstructor;
import module5.pro.model.Food;
import module5.pro.model.Manufacturer;
import module5.pro.repository.FoodRepo;
import module5.pro.repository.ManufacturerRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodApi {
    private final FoodRepo foodRepo;
    private final ManufacturerRepo manufacturerRepo;

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("food",foodRepo.findAll());
        return"food-main";
    }

    @GetMapping("/add")
    public String addFood(Model model){
        List<Manufacturer> manufacturerList=manufacturerRepo.findAll();
        model.addAttribute("countries", manufacturerList);
        return "food-add";
    }

    @PostMapping("/add")
    public String addFood(@RequestParam(name="name") String name,
                          @RequestParam(name="calories") int cal,
                          @RequestParam(name="amount") int amount,
                          @RequestParam(name="price") int price,
                          @RequestParam(name="country_id") Long catId){

        Manufacturer manufacturer=manufacturerRepo.findById(catId).orElse(null);
        Food food=new Food();
        food.setName(name);
        food.setCalories(cal);
        food.setAmount(amount);
        food.setPrice(price);
        food.setManufacturer(manufacturer);

        foodRepo.save(food);
        return"redirect:/food/main";
    }

    @GetMapping("/update/{id}")
    public String edit(Model model,
                       @PathVariable(name="id") Long id){

        Food food=foodRepo.findById(id).orElse(null);
        model.addAttribute("food",food);
        List<Manufacturer> manufacturerList=manufacturerRepo.findAll();
        model.addAttribute("countries", manufacturerList);
        return"update";

    }

    @PostMapping ("/update")
    public String update(@RequestParam("id") Long id,
                         @RequestParam("name")String name,
                         @RequestParam("calories") int cal,
                         @RequestParam("amount") int amount,
                         @RequestParam("price") int price,
                         @RequestParam("country_id") Long catId){
        Manufacturer manufacturer=manufacturerRepo.findById(catId).orElse(null);
        Food food=foodRepo.findById(id).orElseGet(null);
       food.setName(name);
       food.setCalories(cal);
       food.setAmount(amount);
       food.setPrice(price);
       food.setManufacturer(manufacturer);
       foodRepo.save(food);
       return"redirect:/food/main";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        foodRepo.deleteById(id);
        return"redirect:/food/main";
    }

}
