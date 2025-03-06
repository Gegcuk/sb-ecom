package uk.gegc.ecommerce.sbecom.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.gegc.ecommerce.sbecom.service.CategoryService;
import uk.gegc.ecommerce.sbecom.service.ProductService;

import static java.lang.Thread.sleep;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default categories and products on startup
        sleep(3000);
        categoryService.initDbWithDefaultValues();
        productService.initDbWithDefaultValues();
    }
}
