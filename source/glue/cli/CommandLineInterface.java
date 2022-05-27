package cli;

import java.time.Duration;
import java.time.Instant;

public class CommandLineInterface {
    public static void main(String[] args) throws Exception {
        var start_time = Instant.now();
        System.out.println("START");

        var end_time = Instant.now();
        System.out.printf("END( %dms )%n", Duration.between(start_time, end_time).toMillis());
    }
}
