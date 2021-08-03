package quazar.domain.respository;

import quazar.domain.Satellite;

import java.util.List;
import java.util.Optional;

public interface SatelliteRepository {

    List<Satellite> findAll();
    Optional<Satellite> findById(String name);
    Satellite save(Satellite satellite);

}
