package order_closer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import order_closer.domain.dao.OrderClosedRepository;
import order_closer.domain.entities.OrderEntity;
import order_closer.dto.close_order_dto.CloseOrderDTO;
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
    public void takeDataToCloseOrderAndSendToLogs(String json) {
        try {
            CloseOrderDTO orderToClose = mapper.readValue(json, CloseOrderDTO.class);
            OrderEntity orderEntity = getOrderFromDB(orderToClose);

            if(orderEntity != null) {
                closeAndUpdateOrder(orderEntity);
                sendLog(INFO, "Order closing data has been sent successfully");
            } else
                sendLog(WARNING, String.format("Failure! Orders for this data have not been received - " +
                        "{'productId':%d,'requiredQuantity':%d,'spotCoord':{'row':%d,'shelf':%d,'place':%d}}",
                        orderToClose.getProductId(),
                        orderToClose.getSpotCoord().getRow(),
                        orderToClose.getSpotCoord().getShelf(),
                        orderToClose.getSpotCoord().getPlace()));
        } catch (JsonProcessingException e) {
            sendLog(ERROR, e.getMessage());
        }
    }

    private void closeAndUpdateOrder(OrderEntity orderEntity) {
        orderEntity.setState(CLOSED);
        orderClosedRepository.save(orderEntity);
    }

    private OrderEntity getOrderFromDB(CloseOrderDTO closeOrder) {
        Long productId = closeOrder.getProductId();
        Integer row = closeOrder.getSpotCoord().getRow();
        Integer shelf = closeOrder.getSpotCoord().getShelf();
        Integer place = closeOrder.getSpotCoord().getPlace();
        return orderClosedRepository
                .findOrderBy(productId, row, shelf, place);
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
