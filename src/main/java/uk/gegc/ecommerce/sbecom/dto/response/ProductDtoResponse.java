package uk.gegc.ecommerce.sbecom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gegc.ecommerce.sbecom.dto.request.ProductDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoResponse {
    private List<ProductDto> content;

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;

    public ProductDtoResponse(List<ProductDto> categoryDtoList){
        this.content = categoryDtoList;
    }
}
