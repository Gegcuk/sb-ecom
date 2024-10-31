package uk.gegc.ecommerce.sbecom.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gegc.ecommerce.sbecom.model.Category;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private List<CategoryDTO> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean lastPage;
}
