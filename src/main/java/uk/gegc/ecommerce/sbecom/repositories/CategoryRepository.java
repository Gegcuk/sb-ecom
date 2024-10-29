package uk.gegc.ecommerce.sbecom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gegc.ecommerce.sbecom.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
