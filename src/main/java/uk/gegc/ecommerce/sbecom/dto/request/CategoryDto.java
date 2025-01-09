package uk.gegc.ecommerce.sbecom.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gegc.ecommerce.sbecom.model.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;

    @Column(name = "categoryName")
    @Size(min = 5, message = "Category name must be longer than 5 symbols")
    @Size(max = 50, message = "Category name must not be longer that 50 symbols.")
    @NotBlank(message = "Category name must not be blank =(")
    private String CategoryName;

}
