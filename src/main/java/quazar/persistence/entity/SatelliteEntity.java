package quazar.persistence.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="satellite")
public class SatelliteEntity {

    @Id
    private String name;

    @Embedded
    private CoordinateEmbeddable coordinate;

    public SatelliteEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinateEmbeddable getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(CoordinateEmbeddable coordinate) {
        this.coordinate = coordinate;
    }
}
