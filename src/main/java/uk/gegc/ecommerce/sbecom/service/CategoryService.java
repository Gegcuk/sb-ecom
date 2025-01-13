package uk.gegc.ecommerce.sbecom.service;

import jakarta.validation.Valid;
import uk.gegc.ecommerce.sbecom.dto.request.CategoryDto;
import uk.gegc.ecommerce.sbecom.dto.response.CategoryDtoResponse;
import uk.gegc.ecommerce.sbecom.model.Category;

public interface CategoryService {
    CategoryDtoResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDtoResponse createCategory(CategoryDto category);
    CategoryDtoResponse deleteCategory(Long categoryId);
    CategoryDtoResponse updateCategory(Long categoryId, CategoryDto category);

    void initDbWithDefaultValues();
}
