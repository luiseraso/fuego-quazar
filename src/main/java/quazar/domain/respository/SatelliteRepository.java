package quazar.domain.respository;

import quazar.domain.Satellite;

import java.util.Optional;

public interface SatelliteRepository {

    Optional<Satellite> findById(String name);
    Satellite save(Satellite satellite);

}
