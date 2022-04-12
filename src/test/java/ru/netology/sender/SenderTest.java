package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SenderTest {
    @ParameterizedTest
    @MethodSource("argumentsProvider")
    public void test_send(Map<String, String> headers, Location location, Country country, String expMessage) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.anyString()))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country))
                .thenReturn(expMessage);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        String actual = messageSender.send(headers);

        Assertions.assertEquals(expMessage, actual);
    }

    private static Stream<Arguments> argumentsProvider() {
        Map<String, String> rusSegment = new HashMap<>();
        rusSegment.put("1", "127.0.0.1");
        rusSegment.put("2", "127.126.0.0");

        Map<String, String> anotherSegment = new HashMap<>();
        rusSegment.put("1", "96.44.183.149");
        rusSegment.put("2", "96.0.100.100");

        return Stream.of(
                Arguments.of(
                        rusSegment,
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                        Country.RUSSIA,
                        "Добро пожаловать"),
                Arguments.of(
                        anotherSegment,
                        new Location("New York", Country.USA, " 10th Avenue", 32),
                        Country.USA,
                        "Welcome")
        );
    }
}

