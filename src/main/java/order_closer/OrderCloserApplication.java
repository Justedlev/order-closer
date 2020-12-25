package order_closer;

/**
 * @author Edward L. 2020-12-21
 * @project Computerized Storehouse
 *          <p>
 *          OrderCloser – service getting data from a middleware about the orders going to be closed.
 *          The service works with DB for updating orders data (moving to the state “closed”)
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderCloserApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderCloserApplication.class, args);
	}

}
