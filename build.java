import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;

interface build {
    static void main(String[] command_line_arguments) throws Exception {
        var process = new ProcessBuilder()
                .command("echo", "POPO")
                .start()
                .onExit()
                .thenApply(Process::inputReader)
                .thenApply(BufferedReader::lines)
                .thenApply(lines -> lines.collect(joining()))
                .thenAccept(out::println);

    }
}