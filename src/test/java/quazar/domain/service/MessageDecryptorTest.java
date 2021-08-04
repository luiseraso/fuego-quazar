package quazar.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MessageDecryptorTest {

    private MessageDecryptor decryptor = new MessageDecryptor();

    private MockedDataGenerator generator;

    @BeforeEach
    public void fillSatelliteRepository() {
        generator = new MockedDataGenerator();
    }

    @Test
    public void decryptMessageWithoutGaps(){
        var result = decryptor.getMessage(generator.messageWithoutGap());
        assertThat(result.get()).isEqualTo(generator.decryptedMessage());
    }

    @Test
    public void decryptMessageIfSomeMessagesHaveGaps(){
        var result = decryptor.getMessage(generator.messageWithGapsInFirstAndLast());
        assertThat(result.get()).isEqualTo(generator.decryptedMessage());
    }

    @Test
    public void decryptMessageIfAllMessagesHaveGaps(){
        var result = decryptor.getMessage(generator.messageWithGapsInAllMessages());
        assertThat(result.get()).isEqualTo(generator.decryptedMessage());
    }

    @Test
    public void nonDecryptableMesssage(){
        var result = decryptor.getMessage(generator.nonDecryptableMessage());
        assertThat(result).isEqualTo(Optional.empty());
    }

}
