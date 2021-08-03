package quazar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quazar.domain.Satellite;
import quazar.domain.respository.SatelliteRepository;

import java.util.List;

@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(SatelliteRepository satelliteRepository) {
        return args -> {
            List<Satellite> initialSatellites = List.of(
                    new Satellite("skywalker", 100.0, -100.0),
                    new Satellite("sato", 500.0, 100.0),
                    new Satellite("kenobi", -500.0, -200.0)
            );
            initialSatellites.forEach(satelliteRepository::save);
        };
    }
}
