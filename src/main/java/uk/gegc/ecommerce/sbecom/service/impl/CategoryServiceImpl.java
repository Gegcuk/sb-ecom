package uk.gegc.ecommerce.sbecom.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.dto.request.CategoryDto;
import uk.gegc.ecommerce.sbecom.dto.response.CategoryDtoResponse;
import uk.gegc.ecommerce.sbecom.exception.APIException;
import uk.gegc.ecommerce.sbecom.exception.ResourceNotFoundException;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.repository.CategoryRepository;
import uk.gegc.ecommerce.sbecom.service.CategoryService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDtoResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();

        if(categories.isEmpty())
            throw new APIException("No category created till now");

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();

        CategoryDtoResponse categoryDtoResponse = new CategoryDtoResponse();
        categoryDtoResponse.setContent(categoryDtos);
        categoryDtoResponse.setPageNumber(categoryPage.getNumber());
        categoryDtoResponse.setPageSize(categoryPage.getSize());
        categoryDtoResponse.setTotalPages(categoryPage.getTotalPages());
        categoryDtoResponse.setTotalElements(categoryPage.getTotalElements());
        categoryDtoResponse.setLastPage(categoryPage.isLast());

        return categoryDtoResponse;
    }

    @Override
    public CategoryDtoResponse createCategory(CategoryDto categoryToCreate) {
        if (categoryToCreate == null || categoryToCreate.getCategoryName().isBlank())
            throw new APIException("Invalid category data provided");

        Category category = modelMapper.map(categoryToCreate, Category.class);

        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");

        Category createdCategory = categoryRepository.save(category);

        CategoryDtoResponse categoryDtoResponse = new CategoryDtoResponse();
        categoryDtoResponse.setContent(List.of(modelMapper.map(createdCategory, CategoryDto.class)));

        return categoryDtoResponse;
    }

    @Override
    public CategoryDtoResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return new CategoryDtoResponse(List.of(modelMapper.map(category, CategoryDto.class)));
    }

    @Override
    public CategoryDtoResponse updateCategory(Long categoryId, @Valid CategoryDto categoryToUpdate) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Category categoryFromDto = modelMapper.map(categoryToUpdate, Category.class);

        categoryFromDto.setCategoryId(existingCategory.getCategoryId());
        Category savedCategory = categoryRepository.save(categoryFromDto);

        return new CategoryDtoResponse(List.of(modelMapper.map(savedCategory, CategoryDto.class)));
    }

    @Override
    public void initDbWithDefaultValues() {
        List<Category> defaultCategories = Arrays.asList(
                new Category("Football"),
                new Category("Tennis"),
                new Category("Table tennis"),
                new Category("Ice hockey"),
                new Category("Rugby"),
                new Category("Handball"),
                new Category("Basketball"),
                new Category("Boxing"),
                new Category("Volleyball"),
                new Category("American football"),
                new Category("Water polo"),
                new Category("Field hockey"),
                new Category("Badminton"),
                new Category("Futsal"),
                new Category("Baseball"),
                new Category("Snooker"),
                new Category("Beach soccer"),
                new Category("Horse racing"),
                new Category("Beach volleyball"),
                new Category("Winter sports"),
                new Category("Cricket"),
                new Category("Netball")
        );

        categoryRepository.saveAll(defaultCategories);
    }
}
