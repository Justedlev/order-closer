package order_closer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotCoordDTO {
    private Integer row;
    private Integer shelf;
    private Integer place;
}
