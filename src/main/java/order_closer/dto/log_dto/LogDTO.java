package order_closer.dto.log_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class LogDTO {
    String log;

    public LogDTO(LogFormat logFormat) {
        this.log = logFormat.toString();
    }
}
