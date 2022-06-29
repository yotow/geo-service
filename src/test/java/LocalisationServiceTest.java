import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LocalisationServiceTest {

    @ParameterizedTest
    @MethodSource("countryProvider")
    void locateTest(Country country, String expected) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        Assertions.assertEquals(expected, localizationService.locale(country));
    }

    static Stream<Arguments> countryProvider() {
        return Stream.of(
                arguments(Country.USA, "Welcome"),
                arguments(Country.RUSSIA, "Добро пожаловать"),
                arguments(Country.BRAZIL, "Welcome"),
                arguments(Country.GERMANY, "Welcome")
        );
    }
}
