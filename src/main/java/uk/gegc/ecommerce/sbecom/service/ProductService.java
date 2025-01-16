package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.dto.response.ProductDtoResponse;
import uk.gegc.ecommerce.sbecom.model.Product;

public interface ProductService {
    ProductDtoResponse addProduct(Product product, Long categoryId);

    ProductDtoResponse getAllProducts();

    ProductDtoResponse searchByCategory(Long categoryId);

    ProductDtoResponse searchProductByKeyword(String keyword);

    void initDbWithDefaultValues();
}
