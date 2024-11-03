    package uk.gegc.ecommerce.sbecom.repositories;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import uk.gegc.ecommerce.sbecom.model.Category;
    import uk.gegc.ecommerce.sbecom.model.Product;
    import uk.gegc.ecommerce.sbecom.payload.ProductDTO;

    import java.util.List;

    @Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByCategoryOrderByPriceAsc(Category category);

        List<Product> findByProductNameLikeIgnoreCase(String keyword);

    }
