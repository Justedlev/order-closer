package order_closer.domain.dao;

import order_closer.domain.entities.OrderEntity;
import order_closer.domain.entities.OrderStateTypeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface OrderClosedRepository extends MongoRepository<OrderEntity, Object> {

    @Query("{ 'state' : ?0, 'product.productId' : ?1, 'spotCoord.row' : ?2, 'spotCoord.shelf' : ?3, 'spotCoord.place' : ?4 }")
    OrderEntity findOrderBy(OrderStateTypeEntity state, Long productId, Integer row, Integer shelf, Integer place);

}
