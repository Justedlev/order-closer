package order_closer.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SpotCoordEntity {
    private Integer row;
    private Integer shelf;
    private Integer place;
}
