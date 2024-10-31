package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.payload.CategoryDTO;
import uk.gegc.ecommerce.sbecom.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(int pageNumber, int pageSize);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO category, Long categoryId);
}
