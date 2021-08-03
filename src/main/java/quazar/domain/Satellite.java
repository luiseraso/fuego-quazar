package quazar.domain;

public class Satellite {

    private String name;
    private Coordinate position;

    public Satellite() {
    }

    public Satellite(String name, double x, double y) {
        this.name = name;
        this.position = new Coordinate(x, y);
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
