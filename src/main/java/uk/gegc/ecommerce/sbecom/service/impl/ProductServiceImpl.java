package uk.gegc.ecommerce.sbecom.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;
import uk.gegc.ecommerce.sbecom.dto.response.ProductDtoResponse;
import uk.gegc.ecommerce.sbecom.exception.ResourceNotFoundException;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.model.Product;
import uk.gegc.ecommerce.sbecom.repository.CategoryRepository;
import uk.gegc.ecommerce.sbecom.repository.ProductRepository;
import uk.gegc.ecommerce.sbecom.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDtoResponse addProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getPrice() * (product.getDiscount() * 0.01));
        product.setImage("default.png");
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return new ProductDtoResponse(List.of(modelMapper.map(savedProduct, ProductDto.class)));
    }

    @Override
    public ProductDtoResponse getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = productList.stream()
                .map((element) -> modelMapper
                    .map(element, ProductDto.class))
                .toList();
        return new ProductDtoResponse(productDtoList);
    }

    @Override
    public ProductDtoResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> productList = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDto> productDtoList = productList.stream()
                .map(product -> modelMapper
                        .map(product, ProductDto.class))
                .toList();
        return new ProductDtoResponse(productDtoList);
    }
}
