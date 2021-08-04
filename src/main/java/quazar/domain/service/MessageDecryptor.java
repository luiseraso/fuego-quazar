package quazar.domain.service;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class MessageDecryptor {

    public Optional<String> getMessage(String[][] messages) {

        Deque<String> result = new ArrayDeque<>();
        int wordPosition = 1;
        int positionInMessage = 0;

        do {
            boolean emptyWord = true;
            for(int messageNumber=0; messageNumber < messages.length; messageNumber++) {
                positionInMessage = messages[messageNumber].length - wordPosition;

                if(hasShortestMessageEnded(positionInMessage)) {
                    break;
                }

                String word = messages[messageNumber][positionInMessage];
                if(!word.isEmpty()) {
                    result.add(word);
                    wordPosition++;
                    emptyWord = false;
                    break;
                }
            }

            if(isNonInitialWordEmptyInAllTheMessages(emptyWord, positionInMessage)) {
                return Optional.empty();
            }
        } while (positionInMessage > 0);

        StringJoiner joiner = new StringJoiner(" ");
        for(Iterator<String> it = result.descendingIterator(); it.hasNext();) {
            joiner.add(it.next());
        }

        return Optional.of(joiner.toString());
    }

    private boolean hasShortestMessageEnded(int positionInMessage) {
        return positionInMessage < 0;
    }

    private boolean isNonInitialWordEmptyInAllTheMessages(boolean emptyWord, int positionInMessage) {
        return emptyWord && positionInMessage > 0;
    }
}
