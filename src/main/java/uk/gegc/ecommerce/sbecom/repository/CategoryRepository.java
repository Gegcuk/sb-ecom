package uk.gegc.ecommerce.sbecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gegc.ecommerce.sbecom.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
