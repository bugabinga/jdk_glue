/open PRINTING
println("START")

new ProcessBuilder().
command("nu", "--commands", "sleep 2sec ; echo love").
start().
onExit().
thenApply(Process::inputReader).
thenApply(BufferedReader::lines).
thenApply(lines -> lines.collect(Collectors.joining())).
thenAccept(System.out::println).
join()

println("END")
/exit
