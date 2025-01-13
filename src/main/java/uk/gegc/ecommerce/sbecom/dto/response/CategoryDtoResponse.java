package uk.gegc.ecommerce.sbecom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gegc.ecommerce.sbecom.dto.request.CategoryDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoResponse {

    private List<CategoryDto> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;

    public CategoryDtoResponse(List<CategoryDto> content) {
        this.content = content;
    }
}
