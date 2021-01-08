package order_closer.dto.log_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@Setter
public class LogFormat {
    private LocalDateTime dateTime;
    private MessageType messageType;
    private Class<?> service;
    private String message;

    /**
     * return log string in format "yyyy-MM-dd|HH:mm:ss TYPE_LOG [SERVICE_NAME] : MESSAGE"
     */
    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        return String.format("%s %s [%s] : %s", dateTime.format(format), messageType, service.getSimpleName(), message);
    }
}
