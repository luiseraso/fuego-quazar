package quazar.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quazar.domain.Coordinate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    private Coordinate position;
    private String message;

}
