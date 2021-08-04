package quazar.domain.respository;

import quazar.domain.InterceptedMessage;

import java.util.List;

public interface InterceptedMessageRepository {

    List<InterceptedMessage> findAll();
    InterceptedMessage saveOverwriting(InterceptedMessage interceptedMessage);
    void deleteAll();

}
