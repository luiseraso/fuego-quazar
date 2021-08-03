package quazar.persistence.curd;

import org.springframework.data.repository.CrudRepository;
import quazar.persistence.entity.SatelliteEntity;

public interface SatelliteSqlCrudRepository extends CrudRepository<SatelliteEntity, String> {

}
