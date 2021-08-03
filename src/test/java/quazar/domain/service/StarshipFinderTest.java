package quazar.domain.service;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import quazar.domain.Coordinate;
import quazar.domain.Satellite;
import quazar.domain.respository.SatelliteRepository;
import quazar.web.dto.InterceptedMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StarshipFinderTest {

    private static final Map<String, Optional<Satellite>> SATELLITES = Map.ofEntries(
            entry("skywalker", Optional.of(new Satellite("skywalker", -0.4, -2.2))),
            entry("sato", Optional.of(new Satellite("sato", 2.8, -4.6))),
            entry("kenobi", Optional.of(new Satellite("kenobi", -5.2, -3.6))));

    private static final Map<String, Optional<Satellite>> WELL_POSITIONED_SATELLITES = Map.ofEntries(
            entry("skywalker2", Optional.of(new Satellite("skywalker2", 0.0, 0.0))),
            entry("sato2", Optional.of(new Satellite("sato2", 4.0, 0.0))),
            entry("kenobi2", Optional.of(new Satellite("kenobi2", -3.0, -4))));

    @MockBean
    private SatelliteRepository satelliteRepository;

    @Autowired
    private StarshipFinder starshipFinder;

    @BeforeEach
    void init() {
        Mockito.when(satelliteRepository.findById("skywalker"))
                .thenReturn(SATELLITES.get("skywalker"));
        Mockito.when(satelliteRepository.findById("sato"))
                .thenReturn(SATELLITES.get("sato"));
        Mockito.when(satelliteRepository.findById("kenobi"))
                .thenReturn(SATELLITES.get("kenobi"));
        Mockito.when(satelliteRepository.findById("other"))
                .thenReturn(Optional.empty());

        Mockito.when(satelliteRepository.findById("skywalker2"))
                .thenReturn(WELL_POSITIONED_SATELLITES.get("skywalker2"));
        Mockito.when(satelliteRepository.findById("sato2"))
                .thenReturn(WELL_POSITIONED_SATELLITES.get("sato2"));
        Mockito.when(satelliteRepository.findById("kenobi2"))
                .thenReturn(WELL_POSITIONED_SATELLITES.get("kenobi2"));
    }

    @Test
    public void findStarshipWithSolution() {
        List<InterceptedMessage> interceptedMessage = List.of(
                InterceptedMessage.builder()
                        .distance(5.0)
                        .name("skywalker")
                        .build(),
                InterceptedMessage.builder()
                        .distance(8.0623)
                        .name("sato")
                        .build(),
                InterceptedMessage.builder()
                        .distance(8.0)
                        .name("kenobi")
                        .build());

        Coordinate result =  starshipFinder.findStarship(interceptedMessage).get();
        assertThat(result.getX()).isEqualTo(-0.4, Offset.offset(0.1));
        assertThat(result.getY()).isEqualTo(2.8, Offset.offset(0.1));
    }

    @Test
    public void findStarshipUnknownSatellite() {
        List<InterceptedMessage> interceptedMessage = List.of(
                InterceptedMessage.builder()
                        .distance(5.0)
                        .name("skywalker")
                        .build(),
                InterceptedMessage.builder()
                        .distance(8.0623)
                        .name("sato")
                        .build(),
                InterceptedMessage.builder()
                        .distance(8.0)
                        .name("other")
                        .build());

        Optional<Coordinate> result =  starshipFinder.findStarship(interceptedMessage);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void findStarshipWellPositionedSatellites() {
        List<InterceptedMessage> interceptedMessage = List.of(
                InterceptedMessage.builder()
                        .distance(5.0)
                        .name("skywalker2")
                        .build(),
                InterceptedMessage.builder()
                        .distance(8.0623)
                        .name("sato2")
                        .build(),
                InterceptedMessage.builder()
                        .distance(8.0)
                        .name("kenobi2")
                        .build());

        Coordinate result =  starshipFinder.findStarship(interceptedMessage).get();
        assertThat(result.getX()).isEqualTo(-3.0, Offset.offset(0.1));
        assertThat(result.getY()).isEqualTo(4.0, Offset.offset(0.1));
    }

}
