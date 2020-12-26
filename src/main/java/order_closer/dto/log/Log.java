package order_closer.dto.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private LocalDateTime dateTime;
    private MessageType messageType;
    private Class<?> service;
    private String message;

    public Log(LocalDateTime dateTime, MessageType messageType, Class<?> service, String message) {
        this.dateTime = dateTime;
        this.messageType = messageType;
        this.service = service;
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Class<?> getService() {
        return service;
    }

    public void setService(Class<?> service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * return log string in format "yyyy-MM-dd HH:mm:ss TYPE_LOG - [SERVICE_NAME] : MESSAGE"
     */
    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s $s - [%s] : $s", dateTime.format(format), messageType, service.getSimpleName(), message);
    }
}
