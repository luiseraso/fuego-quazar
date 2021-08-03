package quazar.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import quazar.domain.Satellite;
import quazar.domain.respository.SatelliteRepository;
import quazar.persistence.curd.SatelliteSqlCrudRepository;
import quazar.persistence.entity.SatelliteEntity;
import quazar.persistence.mapper.SatelliteMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SatelliteSqlRepository implements SatelliteRepository {

    private final SatelliteSqlCrudRepository satelliteCrudRepository;
    private final SatelliteMapper mapper;

    @Override
    public List<Satellite> findAll() {
        List<SatelliteEntity> satellites = (List<SatelliteEntity>) satelliteCrudRepository.findAll();
        return mapper.toSatellites(satellites);
    }

    @Override
    public Optional<Satellite> findById(String name) {
        return satelliteCrudRepository.findById(name)
                .map(s -> mapper.toSatellite(s));
    }

    @Override
    public Satellite save(Satellite satellite) {
        SatelliteEntity entity = satelliteCrudRepository.save(
                mapper.toSatelliteEntity(satellite));
        return mapper.toSatellite(entity);
    }
}
