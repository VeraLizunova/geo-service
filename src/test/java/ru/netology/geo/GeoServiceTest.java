package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

public class GeoServiceTest {
    @ParameterizedTest
    @MethodSource("ipProvider")
    public void test_byIp(String ip, Location expLocation) {
        GeoService geoService = new GeoServiceImpl();
        Location actual = geoService.byIp(ip);
        Assertions.assertEquals(expLocation, actual);
    }

    private static Stream<Arguments> ipProvider() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.11.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.1.0.0", new Location("New York", Country.USA, null,  0)));
    }
}
