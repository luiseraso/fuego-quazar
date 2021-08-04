package quazar.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import quazar.domain.Coordinate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessageDto {

    private Coordinate position;
    private String message;
    private String errors;

}
