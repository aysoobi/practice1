package module5.pro.api;

import lombok.RequiredArgsConstructor;
import module5.pro.exceptions.CountryNotFoundException;
import module5.pro.exceptions.FoodNotFoundException;
import module5.pro.model.Category;
import module5.pro.model.Food;
import module5.pro.model.Manufacturer;
import module5.pro.repository.CategoriesRepo;
import module5.pro.repository.FoodRepo;
import module5.pro.repository.ManufacturerRepo;
import module5.pro.services.CategoryService;
import module5.pro.services.CountryService;
import module5.pro.services.FoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodApi {
    private final FoodService foodService;
    private final CountryService countryService;
    private final CategoryService categoryService;
    private final FoodRepo foodRepo;

    @GetMapping("/main")
    public String mainPage(Model model,
                           @RequestParam(name = "amount", required = false) Integer amount,
                           @RequestParam(name = "calories", required = false) Integer cal ,
                           @RequestParam(name="name" ,required = false) String name,
                           @RequestParam(name="price", required = false) Integer price,
                           @RequestParam(value = "countryId", required = false) Long countryId
                           ){
        List<Food> foodList=foodRepo.findAllByCriteria(amount,cal,name,price,countryId);
        model.addAttribute("food",foodList);

        List<Manufacturer> manufacturerList=countryService.getCountries();
        model.addAttribute("countries", manufacturerList);
        if(countryId!=null){
            model.addAttribute("chosenCountryId", countryId);
        }
        return"food-main";
    }

    @GetMapping("/add")
    public String addFood(Model model){
        List<Manufacturer> manufacturerList=countryService.getCountries();
        List<Category> categoryList=categoryService.getCategories();
        model.addAttribute("countries", manufacturerList);
        model.addAttribute("categories", categoryList);
        return "food-add";
    }

//    @PostMapping("/add")
//    public String addFood(@RequestParam(name="name") String name,
//                          @RequestParam(name="calories") int cal,
//                          @RequestParam(name="amount") int amount,
//                          @RequestParam(name="price") int price,
//                          @RequestParam(name="country_id") Long catId,
////                          @RequestParam("categories") List<Category> categories
//                         @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds) throws CountryNotFoundException {
//
//        Manufacturer manufacturer=countryService.getCountry(catId);
//        Food food=new Food();
//        food.setName(name);
//        food.setCalories(cal);
//        food.setAmount(amount);
//        food.setPrice(price);
//        food.setManufacturer(manufacturer);
//        if (categoryIds != null && !categoryIds.isEmpty()) {
//            List<Category> selectedCategories =foodService.getAllById(categoryIds);
//            food.setCategories(selectedCategories);
//        }
//        foodService.addFood(food);
//        return"redirect:/food/main";
//    }

    @PostMapping("/add")
    public String addFood(Food food){
        try {
            foodService.addFood(food);
            return  "redirect:/food/main";
        }catch(CountryNotFoundException e){
            return "redirect:/addcar?countryNotFound";
        }

    }



    @GetMapping("/update/{id}")
    public String edit(Model model,
                       @PathVariable(name="id") Long id){

            Food food=foodService.getFood(id);
            model.addAttribute("food", food);
        List<Manufacturer> manufacturerList = countryService.getCountries();

        model.addAttribute("countries", manufacturerList);
        List<Category> categoryList = categoryService.getCategories();
             assert food != null;
             categoryList.removeAll(food.getCategories());
            model.addAttribute("categories", categoryList);
            return "update";

    }
//
//    @PostMapping ("/update")
//    public String update(@RequestParam("id") Long id,
//                         @RequestParam("name")String name,
//                         @RequestParam("calories") int cal,
//                         @RequestParam("amount") int amount,
//                         @RequestParam("price") int price,
//                         @RequestParam("country_id") Long catId) throws CountryNotFoundException {
//        Manufacturer manufacturer=countryService.getCountry(catId);
//        Food food=foodService.getFood(id);
//       food.setName(name);
//       food.setCalories(cal);
//       food.setAmount(amount);
//       food.setPrice(price);
//       food.setManufacturer(manufacturer);
//       foodService.addFood(food);
//       return"redirect:/food/main";
//    }
    @PostMapping("/update")
    public String update(Food food) {
        try {
            foodService.addFood(food);
            return "redirect:/food/main";
        } catch (CountryNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) throws FoodNotFoundException {
        foodService.deleteFood(id);
        return"redirect:/food/main";
    }

    @PostMapping ("/assigncategory")
    public String assignCat(@RequestParam("category_id") Long cat_id,
                            @RequestParam("food_id") Long food_id) throws CountryNotFoundException {

        Food food=foodService.getFood(food_id);

        Category category=categoryService.getCategory(cat_id);
        if(category !=null) {
            List<Category> categories = food.getCategories();
            if (categories == null) {
                categories = new ArrayList<>();
            }
            categories.add(category);
            food.setCategories(categories);
            foodService.addFood(food);
        }
        return "redirect:/food/update/" + food.getId();
    }


    @PostMapping("/unassigncategory")
    public String unassign(@RequestParam("category_id") Long cat_id,
                            @RequestParam("food_id") Long food_id) throws CountryNotFoundException {
        Food food=foodService.getFood(food_id);
        Category category=categoryService.getCategory(cat_id);

        if(category!= null){
            List<Category> categories=food.getCategories();
            if(categories==null){
                categories=new ArrayList<>();
            }
            categories.remove(category);
            food.setCategories(categories);
            foodService.addFood(food);
        }
        return "redirect:/food/update/" + food.getId();
    }
}
