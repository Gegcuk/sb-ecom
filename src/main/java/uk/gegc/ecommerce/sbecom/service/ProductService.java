package uk.gegc.ecommerce.sbecom.service;

import org.springframework.web.multipart.MultipartFile;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;
import uk.gegc.ecommerce.sbecom.dto.response.ProductDtoResponse;

import java.io.IOException;

public interface ProductService {
    ProductDtoResponse addProduct(ProductDto product, Long categoryId);

    ProductDtoResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDtoResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDtoResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    void initDbWithDefaultValues();

    ProductDtoResponse updateProduct(Long productId, ProductDto product);

    ProductDtoResponse delete(Long productId);

    ProductDtoResponse updateProductImage(Long productId, MultipartFile image) throws IOException;
}
