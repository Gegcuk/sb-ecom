package uk.gegc.ecommerce.sbecom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.service.CategoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
        String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable(name = "categoryId") Long categoryId,
                                                 @Valid @RequestBody Category category){
        Category savedCategory = categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>("Category with category id=" + savedCategory.getCategoryId() + " updated", OK);
    }
}
