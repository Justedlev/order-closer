package order_closer.domain.dao;

import order_closer.domain.entities.OrderEntity;
import order_closer.domain.entities.OrderStateTypeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderClosedRepository extends MongoRepository<OrderEntity, Object> {

    OrderEntity findByStateAndProductProductIdAndSpotCoordRowAndSpotCoordShelfAndSpotCoordPlace(OrderStateTypeEntity state, Long productId, Integer row, Integer shelf, Integer place);

}
