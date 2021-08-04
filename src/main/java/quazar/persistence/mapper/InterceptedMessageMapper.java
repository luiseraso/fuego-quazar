package quazar.persistence.mapper;

import org.mapstruct.*;
import quazar.domain.InterceptedMessage;
import quazar.persistence.entity.InterceptedMessageEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SatelliteMapper.class})
public interface InterceptedMessageMapper {

    @Mappings({
            @Mapping(source = "satelliteEntity", target = "satellite")
    })
    InterceptedMessage toInterceptedMessage(InterceptedMessageEntity entity);
    List<InterceptedMessage> toInterceptedMessages(List<InterceptedMessageEntity> list);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    InterceptedMessageEntity toInterceptedMessageEntity(InterceptedMessage interceptedMessage);

}
