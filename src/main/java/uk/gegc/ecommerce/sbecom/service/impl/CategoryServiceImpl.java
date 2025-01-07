package uk.gegc.ecommerce.sbecom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.exception.APIException;
import uk.gegc.ecommerce.sbecom.exception.ResourceNotFoundException;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.repository.CategoryRepository;
import uk.gegc.ecommerce.sbecom.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories;
        categories = categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No category created till now");
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return "Category with id = " + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        category.setCategoryId(existingCategory.getCategoryId());
        Category savedCategory = categoryRepository.save(category);

        return savedCategory;
    }
}
