package order_closer.dto.close_order_dto;

import lombok.*;
import order_closer.dto.SpotCoordDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseOrderDTO {
    private Long productId;
    private SpotCoordDTO spotCoord;
}
