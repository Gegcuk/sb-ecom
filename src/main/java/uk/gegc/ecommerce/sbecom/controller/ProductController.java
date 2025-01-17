package uk.gegc.ecommerce.sbecom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;
import uk.gegc.ecommerce.sbecom.dto.response.ProductDtoResponse;
import uk.gegc.ecommerce.sbecom.model.Product;
import uk.gegc.ecommerce.sbecom.service.ProductService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/public/init-products")
    private void initDbWithDefaultProducts() {
        productService.initDbWithDefaultValues();
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDtoResponse> addProduct(@RequestBody ProductDto product,
                                                         @PathVariable(name = "categoryId") Long categoryId){
        ProductDtoResponse savedProduct = productService.addProduct(product, categoryId);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductDtoResponse> getAllProducts(){
        ProductDtoResponse productDtoResponse = productService.getAllProducts();
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductDtoResponse> getProductsByCategory(@PathVariable (name = "categoryId") Long categoryId){
        ProductDtoResponse productDtoResponse = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/search")
    public ResponseEntity<ProductDtoResponse> getProductsByKeyword(@RequestParam(name = "keyword", required = false) String keyword){
        ProductDtoResponse productDtoResponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDtoResponse> updateProduct(@PathVariable(name = "productId") Long productId,
                                                            @RequestBody ProductDto product){
        ProductDtoResponse productDtoResponse = productService.updateProduct(productId, product);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDtoResponse> deleteProduct(@PathVariable(name = "productId") Long productId){
        ProductDtoResponse productDtoResponse = productService.delete(productId);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

}
