package quazar.web.dto;

import lombok.Builder;
import lombok.Data;
import quazar.domain.InterceptedMessage;

@Data
@Builder
public class InterceptedMessageDto {

    private String name;
    private double distance;
    private String[] messages;

    public InterceptedMessage toInterceptedMessage() {
        return new InterceptedMessage(
                this.getName(),
                this.getDistance(),
                this.getMessages());
    }

}
