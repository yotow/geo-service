import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MessageSenderTest {

    @ParameterizedTest
    @MethodSource("ipAndLocationsProvider")
    public void sendTest(String expected, String ip, Location location) {
        //Заглушка
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(location.getCountry())).thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        Assertions.assertEquals(expected, messageSender.send(headers));
    }

    static Stream<Arguments> ipAndLocationsProvider() {
        return Stream.of(
                arguments(new LocalizationServiceImpl().locale(Country.RUSSIA), "172.*", new Location("Moscow", Country.RUSSIA, null, 0)),
                arguments(new LocalizationServiceImpl().locale(Country.USA), "96.*", new Location("New York", Country.USA, null, 0)),
                arguments(null, "127.*", new Location(null, null, null, 0)),
                arguments(null, "127.0.0.1", new Location(null, null, null, 0)),
                arguments(new LocalizationServiceImpl().locale(Country.USA), "", new Location(null, Country.USA, null, 0))
        );
    }
}
