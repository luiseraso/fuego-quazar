package quazar.persistence.entity;

import javax.persistence.Embeddable;

@Embeddable
public class CoordinateEmbeddable {

    private double x;
    private double y;

    public CoordinateEmbeddable() {

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
