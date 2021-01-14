package order_closer.domain.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotCoordEntity {
    private Integer row;
    private Integer shelf;
    private Integer place;
}
