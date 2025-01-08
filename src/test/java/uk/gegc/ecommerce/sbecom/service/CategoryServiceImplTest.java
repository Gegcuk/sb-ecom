package uk.gegc.ecommerce.sbecom.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import uk.gegc.ecommerce.sbecom.dto.request.CategoryDto;
import uk.gegc.ecommerce.sbecom.dto.response.CategoryDtoResponse;
import uk.gegc.ecommerce.sbecom.exception.APIException;
import uk.gegc.ecommerce.sbecom.exception.ResourceNotFoundException;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.repository.CategoryRepository;
import uk.gegc.ecommerce.sbecom.service.impl.CategoryServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");

        categoryDto = new CategoryDto();
        categoryDto.setCategoryId(1L);
        categoryDto.setCategoryName("Electronics");
    }

    @Test
    void testGetAllCategories_Success() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDtoResponse response = categoryService.getAllCategories();

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("Electronics", response.getContent().get(0).getCategoryName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCategories_NoCategories() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        APIException exception = assertThrows(APIException.class, categoryService::getAllCategories);
        assertEquals("No category created till now", exception.getMessage());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateCategory_Success() {
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(null);
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDtoResponse response = categoryService.createCategory(categoryDto);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("Electronics", response.getContent().get(0).getCategoryName());

        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testCreateCategory_AlreadyExists() {
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category); // Ensure a valid Category object is returned
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(category); // Mock a valid Category object

        APIException exception = assertThrows(APIException.class,
                () -> categoryService.createCategory(categoryDto));

        assertEquals("Category with the name Electronics already exists", exception.getMessage());

        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
        verify(categoryRepository, never()).save(any(Category.class)); // Ensure save is never called
    }

    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        String response = categoryService.deleteCategory(1L);

        assertEquals("Category with id = 1 deleted successfully", response);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.deleteCategory(1L));
        assertEquals("Category not found with categoryId: 1", exception.getMessage());

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).delete(any(Category.class));
    }

    @Test
    void testUpdateCategory_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDtoResponse response = categoryService.updateCategory(1L, categoryDto);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("Electronics", response.getContent().get(0).getCategoryName());

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testUpdateCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.updateCategory(1L, categoryDto));
        assertEquals("Category not found with categoryId: 1", exception.getMessage());

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
