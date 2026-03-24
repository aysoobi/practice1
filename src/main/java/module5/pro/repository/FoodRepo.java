package module5.pro.repository;

import jakarta.transaction.Transactional;
import module5.pro.custom.CustomFoodRepo;
import module5.pro.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface FoodRepo extends JpaRepository<Food, Long>, CustomFoodRepo {
//    List<Food> findAllByCriteria(int amount);
//    List<Food> findFoodByCalories(int calories);
//
//    List<Food> findFoodByNameAndCalories(String name, int calories);

}
