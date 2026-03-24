package module5.pro.services;

import module5.pro.exceptions.CategoryNotFoundException;
import module5.pro.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategory(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category) throws CategoryNotFoundException;
    void deleteCategory(Long id) throws  CategoryNotFoundException;
//    List<Category> getAllById(List<Long> categoryIds);
}
