package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceTest {
    @ParameterizedTest
    @MethodSource("countryProvider")
    public void test_locale(Country country, String expMessage) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String actual = localizationService.locale(country);
        Assertions.assertEquals(expMessage, actual);
    }

    private static Stream<Arguments> countryProvider() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"));
    }
}
