package order_closer.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class OrderEntity {
    @Id
    private Object _id;
    private Long timestamp;
    private String dateTimeString;
    private OrderStateType state;
    private Integer requiredQuantity;
    private SpotCoordEntity spotCoord;
    private ProductEntity product;
}
