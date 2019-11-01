import java.util.Optional;

/**
 * Created by lini on 12/11/16.
 */
public class AppOptional {
    class GPSData {
        private Position pos;
        public GPSData(Position pos) {
            this.pos = pos;
        }

        public Optional<Position> getPosition() {
            return Optional.of(pos);
        }
    };

    class Position {
        private Latitude lat;
        public Position(Latitude lat) {
            this.lat = lat;
        }

        public Optional<Latitude> getLatitude() {
            return Optional.of(lat);
        }
    };

    public class Latitude {
        public String getDirection() {
            return "Latitude";
        }
    }

    /**
     * demonstrates various chained operations on Optional, the result of such an operation is always an Optional
     * where:
     * ofNullable: Returns an Optional describing the specified value, if non-null, otherwise returns an empty Optional.
     * flatMap:    If a value is present, apply the provided Optional-bearing mapping function to it, return that result,
     *             otherwise return an empty Optional.
     * map:        If a value is present, apply the provided mapping function to it, and if the result is non-null, then
     *             return an Optional describing the result. Otherwise return an empty Optional.
     * orElse:     Return the value if present, otherwise return other.
     */
    void testDirectionFromGPSData() {
        System.out.println("testDirectionFromGPSData");
        GPSData gpsData = new GPSData(new Position( new Latitude()));
        String direction = Optional
                .ofNullable(gpsData)
                .flatMap(GPSData::getPosition)
                .flatMap(Position::getLatitude)
                .map(Latitude::getDirection)
                .orElse("None");
        System.out.print("Direction: " + direction);
    }

    /**
     * demonstrates that Optional is as stream with zero or one element
     * the code basically is to replace the following verbose condition checks
     * if (s != null) {
     *     if (s.length() > 0) {
     *         System.out.println(s.toUpper());
     *     }
     * }
     */
    void testOptionalAsStream() {
        System.out.println("testOptionalAsStream: print an empty Optional");
        Optional<String> emptyOptional = Optional.empty();
        emptyOptional
                .map(String::toUpperCase)
                .filter(s->s.length() > 0)
                .ifPresent(System.out::println);

        System.out.println("testOptionalAsStream: print a non-empty Optional");
        Optional<String> optional = Optional.of("text");
        optional.map(String::toUpperCase)
                .filter(s->s.length() > 0)
                .ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        AppOptional app = new AppOptional();
        app.testOptionalAsStream();
        app.testDirectionFromGPSData();
    }
}
