package order_closer.domain.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    private Long productId;
    private String productName;
    private String productUnit;
}
