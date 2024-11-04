    package uk.gegc.ecommerce.sbecom.repositories;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import uk.gegc.ecommerce.sbecom.model.Category;
    import uk.gegc.ecommerce.sbecom.model.Product;

    import java.util.List;

    @Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {
        Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

        Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    }
