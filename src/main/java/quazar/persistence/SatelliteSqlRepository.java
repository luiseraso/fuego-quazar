package quazar.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import quazar.domain.Satellite;
import quazar.domain.respository.SatelliteRepository;
import quazar.persistence.crud.SatelliteSqlCrudRepository;
import quazar.persistence.mapper.SatelliteMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SatelliteSqlRepository implements SatelliteRepository {

    private final SatelliteSqlCrudRepository repository;
    private final SatelliteMapper mapper;

    @Override
    public Optional<Satellite> findById(String name) {
        return repository.findById(name)
                .map(s -> mapper.toSatellite(s));
    }

    @Override
    public Satellite save(Satellite satellite) {
        return mapper.toSatellite(repository.save(
                mapper.toSatelliteEntity(satellite)));
    }
}
