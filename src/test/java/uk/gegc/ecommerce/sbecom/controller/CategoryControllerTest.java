package uk.gegc.ecommerce.sbecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gegc.ecommerce.sbecom.dto.request.CategoryDto;
import uk.gegc.ecommerce.sbecom.dto.response.CategoryDtoResponse;
import uk.gegc.ecommerce.sbecom.service.CategoryService;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto sampleCategoryDto;
    private CategoryDtoResponse sampleCategoryDtoResponse;

    @BeforeEach
    public void setup() {
        sampleCategoryDto = new CategoryDto(1L, "Electronics");
        sampleCategoryDtoResponse = new CategoryDtoResponse(Collections.singletonList(sampleCategoryDto));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Mockito.when(categoryService.getAllCategories()).thenReturn(sampleCategoryDtoResponse);

        mockMvc.perform(get("/api/public/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].categoryId", is(1)))
                .andExpect(jsonPath("$.content[0].categoryName", is("Electronics")));
    }

    @Test
    public void testCreateCategory() throws Exception {
        Mockito.when(categoryService.createCategory(Mockito.any(CategoryDto.class)))
                .thenReturn(sampleCategoryDtoResponse);

        mockMvc.perform(post("/api/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content[0].categoryId", is(1)))
                .andExpect(jsonPath("$.content[0].categoryName", is("Electronics")));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Mockito.when(categoryService.deleteCategory(1L)).thenReturn("Category deleted successfully");

        mockMvc.perform(delete("/api/admin/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully"));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Mockito.when(categoryService.updateCategory(Mockito.eq(1L), Mockito.any(CategoryDto.class)))
                .thenReturn(sampleCategoryDtoResponse);

        mockMvc.perform(put("/api/admin/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].categoryId", is(1)))
                .andExpect(jsonPath("$.content[0].categoryName", is("Electronics")));
    }
}