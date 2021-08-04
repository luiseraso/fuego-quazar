package quazar.domain;

import java.util.Objects;

public class Satellite {

    private String name;
    private Coordinate position;

    public Satellite() {
    }

    public Satellite(String name) {
        this.name = name;
    }

    public Satellite(String name, double x, double y) {
        this.name = name;
        this.position = new Coordinate(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Satellite satellite = (Satellite) o;
        return name.equals(satellite.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate coordinate) {
        this.position = coordinate;
    }

}
