package order_closer.dto.log_dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogDTO {
    String log;

    public LogDTO(LogFormat logFormat) {
        this.log = logFormat.toString();
    }
}
