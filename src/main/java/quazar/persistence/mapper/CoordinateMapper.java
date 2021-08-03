package quazar.persistence.mapper;

import org.mapstruct.Mapper;
import quazar.domain.Coordinate;
import quazar.persistence.entity.CoordinateEmbeddable;

@Mapper(componentModel = "spring")
public interface CoordinateMapper {

    Coordinate toCoordinate(CoordinateEmbeddable coordinateEmbeddable);
    CoordinateEmbeddable toCoordinateEmbeddable(Coordinate coordinate);

}
