package opts;

@FunctionalInterface
public interface Parser {
    Options apply(final String[] command_line_arguments);
}
