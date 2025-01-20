package uk.gegc.ecommerce.sbecom.service;

import org.springframework.web.multipart.MultipartFile;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;
import uk.gegc.ecommerce.sbecom.dto.response.ProductDtoResponse;

import java.io.IOException;

public interface ProductService {
    ProductDtoResponse addProduct(ProductDto product, Long categoryId);

    ProductDtoResponse getAllProducts();

    ProductDtoResponse searchByCategory(Long categoryId);

    ProductDtoResponse searchProductByKeyword(String keyword);

    void initDbWithDefaultValues();

    ProductDtoResponse updateProduct(Long productId, ProductDto product);

    ProductDtoResponse delete(Long productId);

    ProductDtoResponse updateProductImage(Long productId, MultipartFile image) throws IOException;
}
