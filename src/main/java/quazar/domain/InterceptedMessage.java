package quazar.domain;

public class InterceptedMessage {

    private Satellite satellite;
    private double distance;
    private String[] message;

    public InterceptedMessage() {
    }

    public InterceptedMessage(String name, double distance, String[] message) {
        this.satellite = new Satellite(name);
        this.distance = distance;
        this.message = message;
    }

    public InterceptedMessage(Satellite satellite, double distance, String[] message) {
        this.satellite = satellite;
        this.distance = distance;
        this.message = message;
    }

    public Satellite getSatellite() {
        return satellite;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
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
