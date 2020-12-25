package order_closer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long productId;
    private Integer requiredQuantity;
    private SpotCoordDTO spotCoord;
}
