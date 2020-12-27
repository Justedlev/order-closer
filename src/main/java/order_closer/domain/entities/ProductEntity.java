package order_closer.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductEntity {
    private Long productId;
    private String productName;
    private String productUnit;
}
