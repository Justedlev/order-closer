package order_closer;

import order_closer.domain.dao.OrderClosedRepository;
import order_closer.domain.entities.OrderEntity;
import order_closer.domain.entities.OrderStateType;
import order_closer.domain.entities.ProductEntity;
import order_closer.domain.entities.SpotCoordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TestChannelBinderConfiguration.class})
class OrderCloserApplicationTests {

	@MockBean
	OrderClosedRepository repo;

	@Autowired
	InputDestination input;

	@Autowired
	OutputDestination output;

	OrderEntity orderEntity = new OrderEntity("open_order_id_test",
			1L,
			"now",
			OrderStateType.OPEN,
			100,
			new SpotCoordEntity(1, 1, 1),
			new ProductEntity(1L, "product name", "product unit"));

	@BeforeEach
	void before() {
		repo.save(orderEntity);
		Mockito.when(repo.findById(this.orderEntity.get_id())).thenReturn(Optional.of(orderEntity));
		Mockito.when(repo.findOrderBy(1L, 1, 1, 1)).thenReturn(orderEntity);
	}

	@Test
	public void testLogSentAndDB() {
		input.send(new GenericMessage<>("{\"productId\":1,\"spotCoord\":{\"row\":1,\"shelf\":1,\"place\":1}}"));
		byte[] logData = output.receive(0, "mpet18gj-storehouse_logs.destination").getPayload();
		String log = new String(logData);
		assertThat(log).contains("log", "Order closing data has been sent successfully");
		System.out.println(log);

		OrderEntity orderEntity = repo.findById(this.orderEntity.get_id()).orElse(null);
		System.out.println(orderEntity);
		assertThat(orderEntity).isNotNull();
		assertThat(orderEntity.getState()).isEqualTo(OrderStateType.CLOSED);
	}

}
