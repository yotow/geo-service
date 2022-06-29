import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GeoServiceImplTest {

    public static Stream<Arguments> ipLocationProvider() {
        return Stream.of(
                arguments("172.*", new Location("Moscow", Country.RUSSIA, null, 0)),
                arguments("96.*", new Location("New York", Country.USA, null, 0)),
                arguments("0.*", null),
                arguments("127.0.0.1", new Location(null, null, null, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("ipLocationProvider")
    void byIpTest(String ip, Location l) {
        GeoServiceImpl geoService = new GeoServiceImpl();

        Assertions.assertEquals(geoService.byIp(ip), l);
    }
}
