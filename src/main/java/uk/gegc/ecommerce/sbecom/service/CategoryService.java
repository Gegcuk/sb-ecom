package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCaegory(Long categoryId);
}
