package quazar.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterceptedMessage {

    private String name;
    private double distance;
    private String[] messages;

}
