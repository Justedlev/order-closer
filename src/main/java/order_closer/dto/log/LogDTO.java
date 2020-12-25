package order_closer.dto.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private LocalDate date;
    private LocalTime time;
    private MessageType messageType;
    private String serviceName;
    private String message;
}
