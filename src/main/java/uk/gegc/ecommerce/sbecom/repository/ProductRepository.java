package uk.gegc.ecommerce.sbecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gegc.ecommerce.sbecom.model.Category;
import uk.gegc.ecommerce.sbecom.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
