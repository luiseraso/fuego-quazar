package quazar.domain.service;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StarshipFinderTest {

    @Autowired
    private StarshipFinder starshipFinder;

    private MockedDataGenerator generator;

    @BeforeEach
    public void fillSatelliteRepository() {
        generator = new MockedDataGenerator();
    }

    @Test
    public void findStarshipWithSolution() {
        var result =  starshipFinder.getLocation(generator.getCompleteListOfInterceptedMessagesWithSatellites()).get();
        assertThat(result.getX()).isEqualTo(-0.4, Offset.offset(0.1));
        assertThat(result.getY()).isEqualTo(2.8, Offset.offset(0.1));
    }

    @Test
    public void findStarshipUnknownSatellite() {
        var result =  starshipFinder.getLocation(generator.getPartialListOfInterceptedMessagesWithSatellites());
        assertThat(result).isEqualTo(Optional.empty());
    }

}
