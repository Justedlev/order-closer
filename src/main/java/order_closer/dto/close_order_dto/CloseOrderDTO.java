package order_closer.dto.close_order_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import order_closer.dto.SpotCoordDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloseOrderDTO {
    private Long productId;
    private SpotCoordDTO spotCoord;
}
