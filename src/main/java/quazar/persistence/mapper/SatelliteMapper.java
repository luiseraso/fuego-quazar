package quazar.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import quazar.domain.Satellite;
import quazar.persistence.entity.SatelliteEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CoordinateMapper.class})
public interface SatelliteMapper {

    @Mappings({
            @Mapping(source = "coordinate", target = "position")
    })
    Satellite toSatellite(SatelliteEntity entity);
    List<Satellite> toSatellites(List<SatelliteEntity> list);

    @InheritInverseConfiguration
    SatelliteEntity toSatelliteEntity(Satellite satellite);

}
