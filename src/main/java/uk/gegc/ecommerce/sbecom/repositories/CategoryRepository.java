package uk.gegc.ecommerce.sbecom.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.gegc.ecommerce.sbecom.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(@NotBlank @Size(min = 4, message = "Category name must contain at least 4 symbols") String categoryName);
}
