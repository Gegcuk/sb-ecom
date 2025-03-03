package uk.gegc.ecommerce.sbecom.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gegc.ecommerce.sbecom.config.AppConstants;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;
import uk.gegc.ecommerce.sbecom.dto.response.ProductDtoResponse;
import uk.gegc.ecommerce.sbecom.model.Product;
import uk.gegc.ecommerce.sbecom.service.ProductService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/public/init/products")
    private void initDbWithDefaultProducts() {
        productService.initDbWithDefaultValues();
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDtoResponse> addProduct(@Valid @RequestBody ProductDto product,
                                                         @PathVariable(name = "categoryId") Long categoryId){
        ProductDtoResponse savedProduct = productService.addProduct(product, categoryId);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductDtoResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder
    ){
        ProductDtoResponse productDtoResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductDtoResponse> getProductsByCategory(@PathVariable (name = "categoryId") Long categoryId,
                                                                    @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                                    @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                                    @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                                                    @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder
    ){
        ProductDtoResponse productDtoResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/search")
    public ResponseEntity<ProductDtoResponse> getProductsByKeyword(@RequestParam(name = "keyword", required = false) String keyword,
                                                                   @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                                   @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                                   @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                                                   @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder
    ){
        ProductDtoResponse productDtoResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDtoResponse> updateProduct(@PathVariable(name = "productId") Long productId,
                                                            @Valid @RequestBody ProductDto product){
        ProductDtoResponse productDtoResponse = productService.updateProduct(productId, product);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDtoResponse> deleteProduct(@PathVariable(name = "productId") Long productId){
        ProductDtoResponse productDtoResponse = productService.delete(productId);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDtoResponse> updateProductImage(@PathVariable Long productId,
                                                                 @RequestParam("image")MultipartFile image) throws IOException {
        ProductDtoResponse productDtoResponse = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

}
