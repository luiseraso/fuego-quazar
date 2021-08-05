package quazar.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quazar.domain.Coordinate;
import quazar.domain.InterceptedMessage;
import quazar.domain.ResolvedMessage;
import quazar.domain.respository.InterceptedMessageRepository;
import quazar.domain.respository.SatelliteRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunicationManager {

    private final MessageDecryptor messageDecryptor;
    private final StarshipFinder starshipFinder;

    private final InterceptedMessageRepository interceptedMessageRepository;
    private final SatelliteRepository satelliteRepository;

    public Optional<InterceptedMessage> saveSplitMessage(InterceptedMessage newMessage) {
        return verifyValidSatellite(newMessage)
                .map(interceptedMessageRepository::saveOverwriting);
    }

    public Optional<ResolvedMessage> resolveWithSavedMessages() {
        List<InterceptedMessage> interceptedMessages = interceptedMessageRepository.findAll();
        return resolve(interceptedMessages);
    }

    public Optional<ResolvedMessage> resolveWithReceivedMessages(List<InterceptedMessage> interceptedMessages) {
        return resolve(
                interceptedMessages.stream()
                    .map(this::verifyValidSatellite)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .sorted(Comparator.comparing(InterceptedMessage::getDistance))
                    .collect(Collectors.toList()));
    }

    public void removeAllSplitMessages() {
        interceptedMessageRepository.deleteAll();
    }

    private Optional<ResolvedMessage> resolve(List<InterceptedMessage> interceptedMessages) {
        Future<Optional<ResolvedMessage>> resolvedMessageFuture =
                CompletableFuture
                    .supplyAsync(() -> messageDecryptor.getMessage(getArrayOfMessages(interceptedMessages)))
                    .thenCombine(
                        CompletableFuture
                                .supplyAsync(() -> starshipFinder.getLocation(interceptedMessages)),
                    (message, position) ->
                         message.flatMap(m -> position.map(p -> new ResolvedMessage(p, m)))
                    );

        try {
            return resolvedMessageFuture.get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<InterceptedMessage> verifyValidSatellite(InterceptedMessage message) {
        return satelliteRepository.findById(message.getSatellite().getName())
                .map(satellite -> new InterceptedMessage(
                        satellite,
                        message.getDistance(),
                        message.getMessage()));
    }

    private String[][] getArrayOfMessages(List<InterceptedMessage> interceptedMessages) {
        return interceptedMessages.stream()
                .map(InterceptedMessage::getMessage)
                .toArray(String[][]::new);
    }

}
