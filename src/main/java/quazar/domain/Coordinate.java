package quazar.domain;

import java.util.Objects;

public class Coordinate {

    private double x;
    private double y;

    public Coordinate() {
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Coordinate move(double movementInX, double movementInY) {
        return new Coordinate(x + movementInX, y + movementInY);
    }

    public Coordinate moveInverse(double movementInX, double movementInY) {
        return move(-movementInX, -movementInY);
    }

    public Coordinate rotate(double sin, double cos) {
        return new Coordinate(
                x*cos + y*sin,
                -x*sin + y*cos);
    }

    public Coordinate rotateInverse(double sin, double cos) {
        return new Coordinate(
                x*cos - y*sin,
                x*sin + y*cos);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
