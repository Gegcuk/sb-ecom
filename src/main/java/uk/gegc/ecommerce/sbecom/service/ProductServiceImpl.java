package uk.gegc.ecommerce.sbecom.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uk.gegc.ecommerce.sbecom.exceptions.ResourceNotFoundException;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.model.Product;
import uk.gegc.ecommerce.sbecom.payload.ProductDTO;
import uk.gegc.ecommerce.sbecom.repositories.CategoryRepository;
import uk.gegc.ecommerce.sbecom.repositories.ProductRepository;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        product.setCategory(category);
        double specialPrice =product.getDiscount() * 0.01 * product.getPrice();
        product.setSpecalPrice(specialPrice);
        product.setImage("default.png");
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
