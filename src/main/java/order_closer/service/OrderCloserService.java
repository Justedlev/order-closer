package order_closer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import order_closer.domain.dao.OrderClosedRepository;
import order_closer.domain.entities.OrderEntity;
import order_closer.dto.OrderDTO;
import order_closer.dto.log.Log;
import order_closer.dto.log.LogDTO;
import order_closer.dto.log.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        LogDTO log = new LogDTO();
        if(orderEntity == null) {
            log.setLog(createLogAsString(ERROR, "Failed to get data from the database by parameters - " + json));
            sendLog(mapper.writeValueAsString(log));
            return;
        }

        closeAndUpdateOrder(orderEntity);
        log.setLog(createLogAsString(INFO, "Order was closed successfully - " + json));

        sendLog(mapper.writeValueAsString(log));
    }

    private void closeAndUpdateOrder(OrderEntity orderEntity) {
        orderEntity.setState(CLOSED);
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
                .findOrderBy(OPEN, productId, row, shelf, place);
    }

    private String createLogAsString(MessageType type, String message) {
        Log log = new Log(LocalDateTime.now(), type, OrderCloserService.class, message);
        return log.toString();
    }
}
