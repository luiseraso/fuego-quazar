package quazar.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quazar.domain.InterceptedMessage;
import quazar.domain.service.CommunicationManager;
import quazar.web.dto.InterceptedMessageDto;
import quazar.web.dto.RequestMessageDto;
import quazar.web.dto.ResponseMessageDto;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TopSecretController {

    private final CommunicationManager communicationManager;
    private static final String ERROR_MESSAGE = "NO HAY SUFICIENTE INFORMACIÓN";

    @PostMapping(path="topsecret", consumes="application/json")
    public ResponseEntity<ResponseMessageDto> topSecretResolveReceivedMessages(
            @RequestBody RequestMessageDto requestMessageDto) {

         return communicationManager.resolveWithReceivedMessages(
                        requestMessageDto.getSatellites().stream()
                                .map(InterceptedMessageDto::toInterceptedMessage)
                                .collect(Collectors.toList()))

                .map(resolvedMessage -> ResponseMessageDto.builder()
                                            .message(resolvedMessage.getMessage())
                                            .position(resolvedMessage.getPosition())
                                        .build())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path="topsecret_split/{name}", consumes="application/json")
    public ResponseEntity<InterceptedMessage> topSecretSplitSaveSplitMessage(
            @PathVariable String name,
            @RequestBody InterceptedMessageDto interceptedMessageDto) {

        interceptedMessageDto.setName(name);
        return communicationManager.saveSplitMessage(interceptedMessageDto.toInterceptedMessage())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path="topsecret_split")
    public ResponseEntity<ResponseMessageDto> topSecretSplitResolveWithSavedMessages() {
        return communicationManager.resolveWithSavedMessages()
                .map(resolvedMessage ->
                        ResponseEntity.ok(
                                ResponseMessageDto.builder()
                                    .message(resolvedMessage.getMessage())
                                    .position(resolvedMessage.getPosition())
                                    .build()))
                .orElseGet(() -> ResponseEntity.ok(
                                    ResponseMessageDto.builder()
                                        .errors(ERROR_MESSAGE)
                                    .build()));
    }




     /*
    @GetMapping("/sample")
    public RequestMessageDto sampleDto() {
        String[] message1 = {"",     "es", "",   ""};
        String[] message2 = {"este", "",   "un", "mensaje"};
        String[] message3 = {"",     "es", "un", "mensaje"};

        return new RequestMessageDto(
                List.of(
                InterceptedMessageDto.builder()
                        .name("kenobi")
                        .distance(100.0)
                        .messages(message1)
                        .build(),
                InterceptedMessageDto.builder()
                        .name("sato")
                        .distance(142.7)
                        .messages(message2)
                        .build(),
                InterceptedMessageDto.builder()
                        .name("skywalker")
                        .distance(115.5)
                        .messages(message3)
                        .build()
                )
        );
    }
    */
}
