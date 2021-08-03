package quazar.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MessageDecryptorTest {

    private final String[] message1 = {"",     "es", "",   ""};
    private final String[] message2 = {"este", "",   "un", "mensaje"};
    private final String[] message3 = {"",     "es", "un", "mensaje"};

    private final String[] message4 = {"", "este", "es", "",   "mensaje"};
    private final String[] message5 = {"", "",     "es", "un", "mensaje"};
    private final String[] message6 = {"", "",     "",   "un", "mensaje"};

    private final String DECRYPTED_MESSAGE = "este es un mensaje";

    MessageDecryptor decryptor = new MessageDecryptor();

    @Test
    public void decryptMessageWithoutGaps(){
        String[][] messageWithoutGap = {message1, message2, message3};
        var result = decryptor.backwardDecryptor(messageWithoutGap);
        assertThat(result.get()).isEqualTo(DECRYPTED_MESSAGE);
    }

    @Test
    public void decryptMessageIfSomeMessagesHaveGaps(){
        String[][] messageWithGapsInFirstAndLast = {message4, message3, message5};
        var result = decryptor.backwardDecryptor(messageWithGapsInFirstAndLast);
        assertThat(result.get()).isEqualTo(DECRYPTED_MESSAGE);
    }

    @Test
    public void decryptMessageIfAllMessagesHaveGaps(){
        String[][] messageWithGapsInAllMessages = {message4, message5, message6};
        var result = decryptor.backwardDecryptor(messageWithGapsInAllMessages);
        assertThat(result.get()).isEqualTo(DECRYPTED_MESSAGE);
    }

    @Test
    public void nonDecryptableMesssage(){
        String[][] nonDecryptableMessage = {message3, message5, message6};
        var result = decryptor.backwardDecryptor(nonDecryptableMessage);
        assertThat(result).isEqualTo(Optional.empty());
    }

}
