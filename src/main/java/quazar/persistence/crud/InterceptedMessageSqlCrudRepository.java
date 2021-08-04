package quazar.persistence.crud;

import org.springframework.data.repository.CrudRepository;
import quazar.persistence.entity.InterceptedMessageEntity;

import java.util.List;

public interface InterceptedMessageSqlCrudRepository extends CrudRepository<InterceptedMessageEntity, String> {

    List<InterceptedMessageEntity> findBySatelliteEntityName(String name);

}
