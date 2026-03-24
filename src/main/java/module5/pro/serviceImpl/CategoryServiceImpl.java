package module5.pro.serviceImpl;

import lombok.RequiredArgsConstructor;
import module5.pro.exceptions.CategoryNotFoundException;
import module5.pro.model.Category;
import module5.pro.repository.CategoriesRepo;
import module5.pro.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public final CategoriesRepo categoriesRepo;

    @Override
    public List<Category> getCategories() {
        List<Category> categoryList=categoriesRepo.findAll();
        return categoryList;
    }

    @Override
    public Category getCategory(Long id) {
        Category category=categoriesRepo.findById(id).orElse(null);
        return category;
    }

    @Override
    public Category addCategory(Category category) {
        Category category1=categoriesRepo.save(category);
        return category1;
    }

    @Override
    public Category updateCategory(Category category) throws CategoryNotFoundException {
        return categoriesRepo.findById(category.getId())
                .map(category1 -> {
                    return categoriesRepo.save(category1);
                }).orElseThrow(()-> new CategoryNotFoundException());
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
           Category check=getCategory(id);
           if(Objects.isNull(check)){
               throw new CategoryNotFoundException();
           }
           categoriesRepo.deleteById(id);
    }


}
