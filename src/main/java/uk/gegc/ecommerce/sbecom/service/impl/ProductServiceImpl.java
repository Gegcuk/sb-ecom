package uk.gegc.ecommerce.sbecom.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;
import uk.gegc.ecommerce.sbecom.dto.response.ProductDtoResponse;
import uk.gegc.ecommerce.sbecom.exception.APIException;
import uk.gegc.ecommerce.sbecom.exception.ResourceNotFoundException;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.model.Product;
import uk.gegc.ecommerce.sbecom.repository.CategoryRepository;
import uk.gegc.ecommerce.sbecom.repository.ProductRepository;
import uk.gegc.ecommerce.sbecom.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDtoResponse addProduct(ProductDto productDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = modelMapper.map(productDto, Product.class);
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
        if(productList.isEmpty()) throw new APIException("No products added till now");
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

    @Override
    public ProductDtoResponse searchProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDto> productDtoList = products.stream().map((element) -> modelMapper.map(element, ProductDto.class)).toList();
        ProductDtoResponse productDtoResponse = new ProductDtoResponse();
        productDtoResponse.setContent(productDtoList);
        return productDtoResponse;
    }

    @Override
    public ProductDtoResponse updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        Product product = modelMapper.map(productDto, Product.class);
        product.setProductId(existingProduct.getProductId());
        product.setSpecialPrice(product.getPrice() - (product.getPrice() * (product.getDiscount() * 0.01)));
        Product savedProduct = productRepository.save(product);
        return new ProductDtoResponse(List.of(modelMapper.map(savedProduct, ProductDto.class)));
    }

    @Override
    public ProductDtoResponse delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return new ProductDtoResponse(List.of(modelMapper.map(product, ProductDto.class)));
    }


    @Override
    public void initDbWithDefaultValues() {
        Category footballCategory = categoryRepository.findById(1L) // Replace with the actual ID for Football
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", 1L));
        Category tennisCategory = categoryRepository.findById(2L) // Replace with the actual ID for Tennis
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", 2L));
        Category basketballCategory = categoryRepository.findById(7L) // Replace with the actual ID for Basketball
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", 7L));
        Category tableTennisCategory = categoryRepository.findById(3L) // Replace with the actual ID for Table Tennis
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", 3L));

        List<Product> defaultProducts = List.of(
                new Product(null, "Football Ball", "Official size 5 football", 50, "football.png", 25.0, 20.0, 20.0, footballCategory),
                new Product(null, "Football Boots", "Lightweight football boots", 30, "football_boots.png", 60.0, 48.0, 20.0, footballCategory),

                new Product(null, "Tennis Racket", "High-quality graphite tennis racket", 30, "tennis_racket.png", 50.0, 40.0, 20.0, tennisCategory),
                new Product(null, "Tennis Balls", "Pack of 3 durable tennis balls", 100, "tennis_balls.png", 15.0, 12.0, 20.0, tennisCategory),

                new Product(null, "Basketball", "Standard size 7 basketball", 40, "basketball.png", 30.0, 24.0, 20.0, basketballCategory),
                new Product(null, "Basketball Hoop", "Adjustable height basketball hoop", 15, "basketball_hoop.png", 200.0, 180.0, 10.0, basketballCategory),

                new Product(null, "Table Tennis Paddle", "Professional table tennis paddle", 100, "table_tennis_paddle.png", 15.0, 12.0, 20.0, tableTennisCategory),
                new Product(null, "Table Tennis Balls", "Pack of 6 table tennis balls", 150, "table_tennis_balls.png", 10.0, 8.0, 20.0, tableTennisCategory),
                new Product(null, "Table Tennis Table", "Foldable table tennis table", 10, "table_tennis_table.png", 400.0, 350.0, 12.5, tableTennisCategory),

                new Product(null, "Ice Hockey Stick", "Durable ice hockey stick", 20, "ice_hockey_stick.png", 60.0, 50.0, 16.67, null),
                new Product(null, "Ice Hockey Puck", "Official NHL ice hockey puck", 100, "ice_hockey_puck.png", 5.0, 4.0, 20.0, null),

                new Product(null, "Boxing Gloves", "12oz leather boxing gloves", 25, "boxing_gloves.png", 40.0, 32.0, 20.0, null),
                new Product(null, "Volleyball", "Professional volleyball", 50, "volleyball.png", 25.0, 20.0, 20.0, null),
                new Product(null, "Badminton Set", "Complete badminton set for 4 players", 20, "badminton_set.png", 50.0, 45.0, 10.0, null)
        );

        productRepository.saveAll(defaultProducts);
    }
}
