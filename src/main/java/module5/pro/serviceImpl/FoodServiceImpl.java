package module5.pro.serviceImpl;

import lombok.RequiredArgsConstructor;
import module5.pro.exceptions.CategoryNotFoundException;
import module5.pro.exceptions.CountryNotFoundException;
import module5.pro.exceptions.FoodNotFoundException;
import module5.pro.model.Category;
import module5.pro.model.Food;
import module5.pro.model.Manufacturer;
import module5.pro.repository.CategoriesRepo;
import module5.pro.repository.FoodRepo;
import module5.pro.repository.ManufacturerRepo;
import module5.pro.services.FoodService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepo foodRepo;
    private final ManufacturerRepo manufacturerRepo;
    private final CategoriesRepo categoriesRepo;

    private Manufacturer getCountry(Long countryId){
        Manufacturer country=manufacturerRepo.findById(countryId).orElse(null);
        return country;
    }

    private Category getCategory(Long catId){
        Category category=categoriesRepo.findById(catId).orElse(null);
        return category;
    }

    @Override
    public List<Food> getAllFood() {
        List<Food> list=foodRepo.findAll();
        return list;
    }

    @Override
    public Food getFood(Long id) {
        Food f=foodRepo.findById(id).orElse(null);
        return f;
    }

    @Override
    public Food addFood(Food food) throws CountryNotFoundException {
        Manufacturer manufacturer=getCountry(food.getManufacturer().getId());
        if(Objects.isNull(manufacturer)){
            throw new CountryNotFoundException();
        }
         return foodRepo.save(food);
    }

    @Override
    public Food updateFood(Food food) throws FoodNotFoundException, CountryNotFoundException {
//          Food f=foodRepo.findById(food.getId()).orElse(null);
          Food f=getFood(food.getId());
          if(Objects.isNull(f)){
              throw new FoodNotFoundException();
          }

//          Manufacturer m=manufacturerRepo.findById(food.getManufacturer().getId()).orElse(null);

        Manufacturer m=getCountry(food.getManufacturer().getId());
          if(Objects.isNull(m)){
              throw new CountryNotFoundException();
          }

          return foodRepo.save(food);
    }

    @Override
    public void deleteFood(Long id) throws FoodNotFoundException {
        Food f=getFood(id);
        if(Objects.isNull(f)){
            throw new FoodNotFoundException();
        }
        foodRepo.deleteById(id);
    }

    @Override
    public void assignCategory(Long foodId, Long categoryId) throws FoodNotFoundException, CategoryNotFoundException {
        Food food=getFood(foodId);
        if(Objects.isNull(food)){
            throw new FoodNotFoundException();
        }
        Category category=getCategory(categoryId);
        if(Objects.isNull(category)){
            throw new CategoryNotFoundException();
        }

        List<Category> categories=food.getCategories();
        if(Objects.isNull(categories)){
            categories=new ArrayList<>();
        }
       categories.add(category);
        food.setCategories(categories);
        foodRepo.save(food);
    }

    @Override
    public void unassignCategory(Long foodId, Long categoryId) throws FoodNotFoundException, CategoryNotFoundException {
        Food food=getFood(foodId);
        if(Objects.isNull(food)){
            throw new FoodNotFoundException();
        }
        Category category=getCategory(categoryId);
        if(Objects.isNull(category)){
            throw new CategoryNotFoundException();
        }

        List<Category> categoryList=food.getCategories();
        if(Objects.isNull(categoryList)){
            categoryList=new ArrayList<>();
        }
        categoryList.remove(category);
        food.setCategories(categoryList);
        foodRepo.save(food);
    }

    @Override
    public List<Category> loadCategoriesThatDoesNotBelongToFood(Long foodId) throws FoodNotFoundException {
        List<Category> categoryList=categoriesRepo.findAll();
        Food food=getFood(foodId);
        if(Objects.isNull(food)){
            throw new FoodNotFoundException();
        }
         categoryList.removeAll(food.getCategories());
        return categoryList;
    }

    @Override
    public List<Category> getAllById(List<Long> categoryIds){
        List<Category> selectedCategories = categoriesRepo.findAllById(categoryIds);
        return selectedCategories;
    }
}
