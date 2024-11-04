package uk.gegc.ecommerce.sbecom.service;

import org.springframework.web.multipart.MultipartFile;
import uk.gegc.ecommerce.sbecom.payload.ProductDTO;
import uk.gegc.ecommerce.sbecom.payload.ProductResponse;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
