package order_closer.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "orders")
public class OrderEntity {
    private Long timestamp;
    private OrderStateTypeEntity state;
    private Integer requiredQuantity;
    private SpotCoordEntity spotCoord;
    private ProductEntity product;
}
