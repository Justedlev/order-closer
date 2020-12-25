package order_closer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import order_closer.domain.dao.OrderClosedRepository;
import order_closer.domain.entities.OrderEntity;
import order_closer.dto.OrderDTO;
import order_closer.dto.log.LogDTO;
import order_closer.dto.log.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

import static order_closer.domain.entities.OrderStateTypeEntity.*;
import static order_closer.dto.log.MessageType.*;

@EnableBinding(Processor.class)
public class OrderCloserService {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    Processor processor;
    @Autowired
    OrderClosedRepository orderClosedRepository;

    @StreamListener(Processor.INPUT)
    public void takeDataToCloseOrderAndSendToLogs(String json) throws JsonProcessingException {
        OrderDTO order = mapper.readValue(json, OrderDTO.class);
        OrderEntity orderEntity = getOrder(order);

        LogDTO log = new LogDTO(LocalDate.now(), LocalTime.now(), INFO, OrderCloserService.class.getSimpleName(), null);
        if(orderEntity == null) {
            log.setMessageType(WARNING);
            log.setMessage("Failed to get data from the database by parameters - " + json);
            sendLog(mapper.writeValueAsString(log));
            return;
        }

        closeAndUpdateOrder(orderEntity);

        log.setMessage("Order was closed successfully - " + json);
        sendLog(mapper.writeValueAsString(log));
    }

    private void closeAndUpdateOrder(OrderEntity orderEntity) {
        orderEntity.setState(CLOSED);
        orderEntity.setRequiredQuantity(0);
        orderClosedRepository.save(orderEntity);
    }

    private void sendLog(String log) {
        processor.output().send(MessageBuilder.withPayload(log).build()); //out to the 'logs' channel
    }

    private OrderEntity getOrder(OrderDTO order) {
        Long productId = order.getProductId();
        Integer row = order.getSpotCoord().getRow();
        Integer shelf = order.getSpotCoord().getShelf();
        Integer place = order.getSpotCoord().getPlace();
        return orderClosedRepository
                .findByStateAndProductProductIdAndSpotCoordRowAndSpotCoordShelfAndSpotCoordPlace(OPEN, productId, row, shelf, place);
    }

    private LogDTO getLog(MessageType type, String message) {
        String serviceName = OrderCloserService.class.getSimpleName();
        return new LogDTO(LocalDate.now(), LocalTime.now(), type, serviceName, message);
    }
}
