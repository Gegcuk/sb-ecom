package uk.gegc.ecommerce.sbecom.service;

import uk.gegc.ecommerce.sbecom.model.Product;
import uk.gegc.ecommerce.sbecom.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
