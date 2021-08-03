package quazar.web.dto;

import lombok.Builder;
import lombok.Data;
import quazar.domain.Satellite;

@Data
@Builder
public class DistanceToSatellite {

    Satellite satellite;
    double distance;

}
