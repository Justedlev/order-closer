package order_closer.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "orders")
public class OrderEntity {
    @Id
    private Object _id;
    private Long timestamp;
    private String dateTimeString;
    private OrderStateTypeEntity state;
    private Integer requiredQuantity;
    private SpotCoordEntity spotCoord;
    private ProductEntity product;
}
