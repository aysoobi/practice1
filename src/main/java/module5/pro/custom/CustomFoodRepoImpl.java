package module5.pro.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import module5.pro.model.Food;
import module5.pro.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomFoodRepoImpl implements CustomFoodRepo{

    public final EntityManager entityManager;

    @Override
    public List<Food> findAllByCriteria(Integer amount, Integer calories, String name, Integer price, Long countryId) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Food> criteriaQuery=criteriaBuilder.createQuery(Food.class);
        Root<Food> root= criteriaQuery.from(Food.class);
        List<Predicate> predicates=new ArrayList<>();

        if(amount!=null){
            predicates.add(criteriaBuilder.equal(root.get("amount"), amount));
        }
        if(calories!=null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("calories"), calories));
        }
        if(name!=null && !name.isEmpty()){
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
        }
        if(price!=null){
            predicates.add(criteriaBuilder.equal(root.get("price"), price));
        }
        if(countryId!=null){
            Join<Food, Manufacturer> manufacturerJoin=root.join("manufacturer");
            predicates.add(criteriaBuilder.equal(manufacturerJoin.get("id"), countryId));
        }
        if(!predicates.isEmpty()){
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        TypedQuery<Food>typedQuery= entityManager.createQuery(criteriaQuery);
        List<Food> foodList=typedQuery.getResultList();
        return foodList;
    }
}
