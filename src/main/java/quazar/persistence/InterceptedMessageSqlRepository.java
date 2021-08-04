package quazar.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import quazar.domain.InterceptedMessage;
import quazar.domain.respository.InterceptedMessageRepository;
import quazar.persistence.crud.InterceptedMessageSqlCrudRepository;
import quazar.persistence.entity.InterceptedMessageEntity;
import quazar.persistence.mapper.InterceptedMessageMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterceptedMessageSqlRepository implements InterceptedMessageRepository {

    private final InterceptedMessageSqlCrudRepository repository;
    private final InterceptedMessageMapper mapper;

    @Override
    public List<InterceptedMessage> findAll() {
        List<InterceptedMessageEntity> messages = (List<InterceptedMessageEntity>) repository.findAll();
        return mapper.toInterceptedMessages(messages);
    }

    @Override
    public InterceptedMessage saveOverwriting(InterceptedMessage interceptedMessage) {
        List<InterceptedMessageEntity> oldMessage = repository.findBySatelliteEntityName(
                interceptedMessage.getSatellite().getName());

        repository.deleteAll(oldMessage);

        return mapper.toInterceptedMessage(
                repository.save(mapper.toInterceptedMessageEntity(interceptedMessage))
        );
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
