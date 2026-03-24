package module5.pro.custom;

import module5.pro.model.Food;

import java.util.List;

public interface CustomFoodRepo {
    List<Food> findAllByCriteria(Integer amount, Integer calories, String name, Integer price, Long countryId);
}
