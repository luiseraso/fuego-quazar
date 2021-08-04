package quazar.domain.service;

import quazar.domain.InterceptedMessage;
import quazar.domain.Satellite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public class MockedDataGenerator {

    private String[][] messages = {
            {"",     "es", "",   ""},
            {"este", "",   "un", "mensaje"},
            {"",     "es", "un", "mensaje"},

            {"", "este", "es", "",   "mensaje"},
            {"", "",     "es", "un", "mensaje"},
            {"", "",     "",   "un", "mensaje"}
    };

    private String decryptedMessage = "este es un mensaje";

    private Map<String, Satellite> satellites = Map.ofEntries(
            entry("skywalker", new Satellite("skywalker", -0.4, -2.2)),
            entry("sato", new Satellite("sato", 2.8, -4.6)),
            entry("kenobi", new Satellite("kenobi", -5.2, -3.6)));

    private List<InterceptedMessage> interceptedMessagesWithSatellites = List.of(
            new InterceptedMessage(satellites.get("skywalker"), 5.0, messages[0]),
            new InterceptedMessage(satellites.get("sato"), 8.0623, messages[1]),
            new InterceptedMessage(satellites.get("kenobi"), 8.0, messages[2])
    );

    private List<InterceptedMessage> interceptedMessages = List.of(
            new InterceptedMessage("skywalker", 5.0, messages[0]),
            new InterceptedMessage("sato", 8.0623, messages[1]),
            new InterceptedMessage("kenobi", 8.0, messages[2])
    );

    public String decryptedMessage() {
        return decryptedMessage;
    }

    public String[][] messageWithoutGap(){
        return new String[][]{messages[0], messages[1], messages[2]};
    }

    public String[][] messageWithGapsInFirstAndLast() {
        return new String[][]{messages[3], messages[2], messages[4]};
    }

    public String[][] messageWithGapsInAllMessages() {
        return new String[][]{messages[3], messages[4], messages[5]};
    }

    public String[][] nonDecryptableMessage() {
        return new String[][]{messages[2], messages[4], messages[5]};
    }

    public Optional<Satellite> getDefaultSatellite(String name) {
        return Optional.ofNullable(satellites.get(name));
    }

    public List<InterceptedMessage> getCompleteListOfInterceptedMessagesWithSatellites() {
        return new ArrayList<>(interceptedMessagesWithSatellites);
    }

    public List<InterceptedMessage> getPartialListOfInterceptedMessagesWithSatellites() {
        return new ArrayList<>(interceptedMessagesWithSatellites.subList(0,1));
    }

    public List<InterceptedMessage> getCompleteListOfInterceptedMessages() {
        return new ArrayList<>(interceptedMessages);
    }

    public List<InterceptedMessage> getPartialListOfInterceptedMessages() {
        return new ArrayList<>(interceptedMessages.subList(0,1));
    }

}
