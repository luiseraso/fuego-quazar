package quazar.domain.service;

import org.springframework.stereotype.Service;
import quazar.domain.Coordinate;
import quazar.domain.InterceptedMessage;
import quazar.domain.Satellite;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.pow;

@Service
public class StarshipFinder {

    public Optional<Coordinate> getLocation(List<InterceptedMessage> interceptedMessages) {
        if (interceptedMessages.size() < 3) {
            return Optional.empty();
        }
        return Optional.of(relocateSatellitesAndFindStarship(interceptedMessages));
    }

    private Coordinate relocateSatellitesAndFindStarship(List<InterceptedMessage> interceptedMessages) {
        Satellite centralSatellite =  interceptedMessages.get(0).getSatellite();
        double movementInX = - centralSatellite.getPosition().getX();
        double movementInY = - centralSatellite.getPosition().getY();
        moveSatellites(interceptedMessages, movementInX, movementInY);

        Satellite baseRotationSatellite =  interceptedMessages.get(1).getSatellite();
        double sin = calculateSine(baseRotationSatellite.getPosition());
        double cos = calculateCosine(baseRotationSatellite.getPosition());
        rotateSatellites(interceptedMessages, sin, cos);

        Coordinate canonicalCoordinate = findStarshipWithRelocatedSatellites(interceptedMessages);
        return canonicalCoordinate.rotateInverse(sin, cos).moveInverse(movementInX, movementInY);
    }

    private Coordinate findStarshipWithRelocatedSatellites(List<InterceptedMessage> distanceToSatellites) {
        double d0 = distanceToSatellites.get(0).getDistance();

        Satellite baseRotationSatellite = distanceToSatellites.get(1).getSatellite();
        double d1 = distanceToSatellites.get(1).getDistance();

        Satellite freePositionSatellite = distanceToSatellites.get(2).getSatellite();
        double d2 = distanceToSatellites.get(2).getDistance();

        double x = (pow(d0, 2) - pow(d1, 2) + pow(baseRotationSatellite.getPosition().getX(), 2))
                /(2*baseRotationSatellite.getPosition().getX());
        double y = (pow(d0, 2) - pow(d2, 2) + pow(freePositionSatellite.getPosition().getX(), 2)
                + pow(freePositionSatellite.getPosition().getY(), 2))/(2*freePositionSatellite.getPosition().getY())
                - (freePositionSatellite.getPosition().getX()*x)/freePositionSatellite.getPosition().getY();

        return new Coordinate(x, y);
    }

    private void moveSatellites(List<InterceptedMessage> messageWithSatellites, double movementInX, double movementInY) {
        messageWithSatellites.forEach(
                m -> m.getSatellite().setPosition(
                        m.getSatellite().getPosition().move(movementInX, movementInY)));
    }

    private void rotateSatellites(List<InterceptedMessage> messageWithSatellites, double sin, double cos) {
        messageWithSatellites.forEach(
                m -> m.getSatellite().setPosition(
                        m.getSatellite().getPosition().rotate(sin, cos)));
    }

    private double calculateHypotenuse(Coordinate coordinate) {
        return Math.sqrt(pow(coordinate.getX(), 2) + pow(coordinate.getY(), 2));
    }

    private double calculateSine(Coordinate coordinate) {
        return coordinate.getY() / calculateHypotenuse(coordinate);
    }

    private double calculateCosine(Coordinate coordinate) {
        return coordinate.getX() / calculateHypotenuse(coordinate);
    }
}
