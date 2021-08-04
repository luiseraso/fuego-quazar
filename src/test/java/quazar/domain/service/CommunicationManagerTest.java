package quazar.domain.service;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import quazar.domain.respository.InterceptedMessageRepository;
import quazar.domain.respository.SatelliteRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommunicationManagerTest {

    private MockedDataGenerator generator;

    @Autowired
    private CommunicationManager communicationManager;

    @MockBean
    private InterceptedMessageRepository interceptedMessageRepository;

    @MockBean
    private SatelliteRepository satelliteRepository;

    @BeforeEach
    public void fillSatelliteRepository() {
        generator = new MockedDataGenerator();
        List.of("skywalker", "sato", "kenobi", "other")
                .forEach(this::arrangeSatelliteRepository);
    }

    @Test
    public void verifyResolveWithReceivedMessages() {
        var result = communicationManager.resolveWithReceivedMessages(
                generator.getCompleteListOfInterceptedMessages());

        assertThat(result.get().getPosition().getX()).isEqualTo(-0.4, Offset.offset(0.1));
        assertThat(result.get().getPosition().getY()).isEqualTo(2.8, Offset.offset(0.1));
        assertThat(result.get().getMessage()).isEqualTo(generator.decryptedMessage());
    }

    @Test
    public void verifyIncompleteResolveWithReceivedMessages() {
        var result = communicationManager.resolveWithReceivedMessages(
                generator.getPartialListOfInterceptedMessages());

        assertThat(result).isEmpty();
    }

    @Test
    public void verifyCompleteResolveWithSavedMessages() {
        Mockito.when(interceptedMessageRepository.findAll())
                .thenReturn(generator.getCompleteListOfInterceptedMessagesWithSatellites());

        var result = communicationManager.resolveWithSavedMessages();

        assertThat(result.get().getPosition().getX()).isEqualTo(-0.4, Offset.offset(0.1));
        assertThat(result.get().getPosition().getY()).isEqualTo(2.8, Offset.offset(0.1));
        assertThat(result.get().getMessage()).isEqualTo(generator.decryptedMessage());
    }

    @Test
    public void verifyIncompleteResolveWithSavedMessages() {
        Mockito.when(interceptedMessageRepository.findAll())
                .thenReturn(generator.getPartialListOfInterceptedMessagesWithSatellites());

        var result = communicationManager.resolveWithSavedMessages();

        assertThat(result).isEmpty();
    }

    private void arrangeSatelliteRepository(String name){
        Mockito.when(satelliteRepository.findById(name))
                .thenReturn(generator.getDefaultSatellite(name));
    }

}
