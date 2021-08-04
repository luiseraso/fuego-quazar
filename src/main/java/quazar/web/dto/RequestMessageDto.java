package quazar.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestMessageDto {

    private List<InterceptedMessageDto> satellites;

}
