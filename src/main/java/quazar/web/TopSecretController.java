package quazar.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quazar.domain.Coordinate;
import quazar.domain.service.MessageDecryptor;
import quazar.domain.service.StarshipFinder;
import quazar.web.dto.InterceptedMessage;
import quazar.web.dto.RequestMessage;
import quazar.web.dto.ResponseMessage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topsecret")
@RequiredArgsConstructor
public class TopSecretController {

    private final StarshipFinder starshipFinder;
    private final MessageDecryptor messageDecryptor;

    @GetMapping("/sample")
    public RequestMessage sampleDto() {
        String[] message1 = {"",     "es", "",   ""};
        String[] message2 = {"este", "",   "un", "mensaje"};
        String[] message3 = {"",     "es", "un", "mensaje"};

        return new RequestMessage(
                List.of(
                InterceptedMessage.builder()
                        .name("kenobi")
                        .distance(100.0)
                        .messages(message1)
                        .build(),
                InterceptedMessage.builder()
                        .name("sato")
                        .distance(142.7)
                        .messages(message2)
                        .build(),
                InterceptedMessage.builder()
                        .name("skywalker")
                        .distance(115.5)
                        .messages(message3)
                        .build()
                )
        );
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<ResponseMessage> findStarshipPosition(@RequestBody RequestMessage requestMessage) {

        List<String[]> result = requestMessage.getSatellites().stream()
                                                .map(m -> m.getMessages())
                                                .collect(Collectors.toList());
        String[][] encryptedMessages = new String[result.size()][];

        encryptedMessages = result.toArray(encryptedMessages);
        Optional<String> message = messageDecryptor.backwardDecryptor(encryptedMessages);

        Optional<Coordinate> position = starshipFinder.findStarship(requestMessage.getSatellites());

        return message.flatMap(m -> position
                                .map( p -> ResponseEntity.ok(new ResponseMessage(p, m))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
