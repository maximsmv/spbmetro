import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    private StationIndex stationIndex = new StationIndex();
    private List<Station> shortestRoutWithoutChange = new ArrayList<>();
    private List<Station> shortestRoutWithOneChange = new ArrayList<>();
    private List<Station> shortestRoutWithTwoChange = new ArrayList<>();
    private RouteCalculator routeCalculator;

    @Override
    protected void setUp() throws Exception {

        Line line1 = new Line(1, "Кировско-Выборгская");
        Line line2 = new Line(2, "Московско-Петроградская");
        Line line3 = new Line(3, "Невско-Василеостровская");

        line1.addStation(new Station("Девяткино", line1));
        line1.addStation(new Station("Гражданский проспект", line1));
        line1.addStation(new Station("Академическая", line1));
        line2.addStation(new Station("Парнас", line2));
        line2.addStation(new Station("Проспект Просвещения", line2));
        line2.addStation(new Station("Озерки", line2));
        line2.addStation(new Station("Удельная", line2));
        line3.addStation(new Station("Беговая", line3));
        line3.addStation(new Station("Новокрестовская", line3));
        line3.addStation(new Station("Приморская", line3));

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        stationIndex.addStation(line1.getStations().get(0));
        stationIndex.addStation(line1.getStations().get(1));
        stationIndex.addStation(line1.getStations().get(2));
        stationIndex.addStation(line2.getStations().get(0));
        stationIndex.addStation(line2.getStations().get(1));
        stationIndex.addStation(line2.getStations().get(2));
        stationIndex.addStation(line2.getStations().get(3));
        stationIndex.addStation(line3.getStations().get(0));
        stationIndex.addStation(line3.getStations().get(1));
        stationIndex.addStation(line3.getStations().get(2));

        List<Station> connection1 = new ArrayList<>();
        List<Station> connection2 = new ArrayList<>();

        connection1.add(line1.getStations().get(2));
        connection1.add(line2.getStations().get(0));
        connection2.add(line2.getStations().get(2));
        connection2.add(line3.getStations().get(0));

        stationIndex.addConnection(connection1);
        stationIndex.addConnection(connection2);

        shortestRoutWithTwoChange.add(line1.getStations().get(0));
        shortestRoutWithTwoChange.add(line1.getStations().get(1));
        shortestRoutWithTwoChange.add(line1.getStations().get(2));
        shortestRoutWithTwoChange.add(line2.getStations().get(0));
        shortestRoutWithTwoChange.add(line2.getStations().get(1));
        shortestRoutWithTwoChange.add(line2.getStations().get(2));
        shortestRoutWithTwoChange.add(line3.getStations().get(0));

        shortestRoutWithOneChange.add(line1.getStations().get(0));
        shortestRoutWithOneChange.add(line1.getStations().get(1));
        shortestRoutWithOneChange.add(line1.getStations().get(2));
        shortestRoutWithOneChange.add(line2.getStations().get(0));

        shortestRoutWithoutChange.add(line2.getStations().get(3));
        shortestRoutWithoutChange.add(line2.getStations().get(2));
        shortestRoutWithoutChange.add(line2.getStations().get(1));
        shortestRoutWithoutChange.add(line2.getStations().get(0));

        routeCalculator = new RouteCalculator(stationIndex);
    }

    @Test
    @DisplayName("Поиск пути без пересадок")
    public void testGetShortestRouteWithoutChange() {
        List<Station> expected = new ArrayList<>(shortestRoutWithoutChange);
        List<Station> actual = new ArrayList<>(routeCalculator.getShortestRoute(stationIndex.getLine(2).getStations().get(3), stationIndex.getLine(2).getStations().get(0)));
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    @DisplayName("Поиск пути с одной пересадкой")
    public void testGetShortestRouteWithOneChange() {
        List<Station> expected = new ArrayList<>(shortestRoutWithOneChange);
        List<Station> actual = new ArrayList<>(routeCalculator.getShortestRoute(stationIndex.getLine(1).getStations().get(0), stationIndex.getLine(2).getStations().get(0)));
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    @DisplayName("Поиск пути с двумя пересадками")
    public void testGetShortestRouteWithTwoChange() {
        List<Station> expected = new ArrayList<>(shortestRoutWithTwoChange);
        List<Station> actual = new ArrayList<>(routeCalculator.getShortestRoute(stationIndex.getLine(1).getStations().get(0), stationIndex.getLine(3).getStations().get(0)));
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    @DisplayName("Время в пути без пересадок")
    public void testCalculateDurationWithoutChange() {
        double expected = 7.5;
        double actual = RouteCalculator.calculateDuration(shortestRoutWithoutChange);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Время в пути с одной пересадкой")
    public void testCalculateDurationWithOneChange() {
        double expected = 8.5;
        double actual = RouteCalculator.calculateDuration(shortestRoutWithOneChange);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Время в пути с двумя пересадками")
    public void testCalculateDurationWithTwoChange() {
        double expected = 17;
        double actual = RouteCalculator.calculateDuration(shortestRoutWithTwoChange);
        assertEquals(expected, actual);
    }

}