package uk.gegc.ecommerce.sbecom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.ecommerce.sbecom.config.AppConstants;
import uk.gegc.ecommerce.sbecom.dto.request.CategoryDto;
import uk.gegc.ecommerce.sbecom.dto.response.CategoryDtoResponse;
import uk.gegc.ecommerce.sbecom.service.CategoryService;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/public/init")
    private void initDbWithDefault(){
        categoryService.initDbWithDefaultValues();
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryDtoResponse> getAllCategories(
            @RequestParam(name = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        CategoryDtoResponse categoryDtoResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryDtoResponse, OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDtoResponse> createCategory(@Valid @RequestBody CategoryDto category){
        CategoryDtoResponse categoryDtoResponse = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryDtoResponse, CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDtoResponse> deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
        CategoryDtoResponse deletedCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategory, OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDtoResponse> updateCategory(@PathVariable(name = "categoryId") Long categoryId,
                                                 @Valid @RequestBody CategoryDto category){
        CategoryDtoResponse savedCategory = categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(savedCategory, OK);
    }
}
