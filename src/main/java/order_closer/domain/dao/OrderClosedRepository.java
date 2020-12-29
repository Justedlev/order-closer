package order_closer.domain.dao;

import order_closer.domain.entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface OrderClosedRepository extends MongoRepository<OrderEntity, Object> {

    @Query("{ 'product.productId' : ?0, 'spotCoord.row' : ?1, 'spotCoord.shelf' : ?2, 'spotCoord.place' : ?3 }")
    OrderEntity findOrderBy(Long productId, Integer row, Integer shelf, Integer place);

}
