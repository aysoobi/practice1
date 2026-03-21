package module5.pro.api;

import lombok.RequiredArgsConstructor;
import module5.pro.model.Category;
import module5.pro.repository.CategoriesRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryApi {
    private final CategoriesRepo categoriesRepo;


    @GetMapping()
    public String addCategory(){
        return "category-add";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam(name="category_name") String name){
        Category category=new Category();
        category.setName(name);
        categoriesRepo.save(category);
        return "redirect:/category";
    }
}
