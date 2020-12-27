package order_closer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import order_closer.domain.dao.OrderClosedRepository;
import order_closer.domain.entities.OrderEntity;
import order_closer.dto.OrderDTO;
import order_closer.dto.log_dto.LogDTO;
import order_closer.dto.log_dto.LogFormat;
import order_closer.dto.log_dto.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;

import static order_closer.domain.entities.OrderStateTypeEntity.*;
import static order_closer.dto.log_dto.MessageType.*;

@EnableBinding(Processor.class)
public class OrderCloserService {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    Processor processor;
    @Autowired
    OrderClosedRepository orderClosedRepository;

    @StreamListener(Processor.INPUT)
    public void takeDataToCloseOrderAndSendToLogs(String json) throws JsonProcessingException {
        System.out.println(json);
        OrderDTO order = mapper.readValue(json, OrderDTO.class);
        OrderEntity orderEntity = getOrder(order);

        if(orderEntity != null) {
            closeAndUpdateOrder(orderEntity);
            sendLog(INFO, "Order was closed successfully - " + json);
        } else
            sendLog(ERROR, "Failed to get data from the database by parameters - " + json);
    }

    private void closeAndUpdateOrder(OrderEntity orderEntity) {
        orderEntity.setState(CLOSED);
        orderClosedRepository.save(orderEntity);
    }

    private OrderEntity getOrder(OrderDTO order) {
        Long productId = order.getProductId();
        Integer row = order.getSpotCoord().getRow();
        Integer shelf = order.getSpotCoord().getShelf();
        Integer place = order.getSpotCoord().getPlace();
        return orderClosedRepository
                .findOrderBy(OPEN, productId, row, shelf, place);
    }

    private void sendLog(MessageType type, String message) {
        try {
            LogFormat logFormat = new LogFormat(LocalDateTime.now(), type, OrderCloserService.class, message);
            String logJson = mapper.writeValueAsString(new LogDTO(logFormat));
            processor.output().send(MessageBuilder.withPayload(logJson).build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
