package uk.gegc.ecommerce.sbecom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Category {
    private Long categoryId;
    private String categoryName;
}
