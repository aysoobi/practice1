package module5.pro.services;

import module5.pro.exceptions.CategoryNotFoundException;
import module5.pro.exceptions.CountryNotFoundException;
import module5.pro.exceptions.FoodNotFoundException;
import module5.pro.model.Category;
import module5.pro.model.Food;

import java.util.List;

public interface FoodService {
    List<Food> getAllFood();
    Food getFood(Long id);
    Food addFood(Food food) throws CountryNotFoundException;
    Food updateFood(Food food) throws FoodNotFoundException, CountryNotFoundException;
    void deleteFood(Long id) throws FoodNotFoundException;

    void assignCategory(Long foodId, Long categoryId) throws FoodNotFoundException, CategoryNotFoundException;
    void unassignCategory(Long foodId, Long categoryId) throws FoodNotFoundException, CategoryNotFoundException;
    List<Category> loadCategoriesThatDoesNotBelongToFood(Long foodId) throws FoodNotFoundException;
    List<Category> getAllById(List<Long> categoryIds);

}
