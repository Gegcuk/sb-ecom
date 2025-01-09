package uk.gegc.ecommerce.sbecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "categoryName")
    @Size(min = 5, message = "Category name must be longer than 5 symbols")
    @Size(max = 50, message = "Category name must not be longer that 50 symbols.")
    @NotBlank(message = "Category name must not be blank =(")
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
