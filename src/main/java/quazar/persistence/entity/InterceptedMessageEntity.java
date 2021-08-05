package quazar.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="intercepted_message")
public class InterceptedMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "satellite")
    private SatelliteEntity satelliteEntity;
    private double distance;
    private String[] message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SatelliteEntity getSatelliteEntity() {
        return satelliteEntity;
    }

    public void setSatelliteEntity(SatelliteEntity satelliteEntity) {
        this.satelliteEntity = satelliteEntity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
