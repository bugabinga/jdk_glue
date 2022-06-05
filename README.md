# JDK Glue

DSL/script language for combining JDK tools.

## Example

Start the exploration with some idealized, wishful pseudo code.

```java
requires java.base // integrate module-info.java

var source = `source` // path literals are enclosed by `
var output = `output`
var output_modules = `output` + `modules` // join paths with overloaded operator, similar to String
var output_javadoc = `output` + `modules`
var java_version = 17
var encoding = "UTF-8"
var main = "some_module/Main"

// JDK tools

javac(
    "-Werror",
    "-g:none",
    "-encoding", encoding,
    "--release", java_version,
    "--module-source-path", source,
    "-d", output_modules
)

javadoc(
    "-Werror",
    "-locale", "de",
    "-encoding", encoding,
    "-docencoding", encoding,
    "-charset", encoding,
    "-keywords",
    "-use",
    "-version",
    "-linksource",
    "--module-source-path", source,
    "--release", java_version,
    "-d", output_javadoc
)

java(
    "--show-version",
    "--source", java_version,
    "--module-path", output_modules,
    main
)
// semantically same as:
new ProcessBuilder()
.command("java", "--show-version", "--source", java_version, "--module-path", output_modules, main)
.inheritIO()
.start()

jwebserver(
    "--bind-address", "127.0.0.1",
    "--port", 8080,
    "--directory", output_javadoc
)

// System tools ?

var commit_count = git(
    "rev-list", "--count", "--first-parent", "HEAD"
)
for( int index = 0; index <= Integer.parse(commit_count); ++index )
{
    echo("Yay ", index);
}

```

## Experiments

### JShell

_JShell_ has a way to handle script files without semicolons.

```java
/open PRINTING
println("START")

var ps = new ProcessBuilder().
command( "ps", "aux").
inheritIO().
start()

//nothing gets printed, despite `inheritIO`

new ProcessBuilder().
command("nu", "--commands", "sleep 2sec ; echo love").
start().
onExit().
thenApply(Process::inputReader).
thenApply(BufferedReader::lines).
thenApply(lines -> lines.collect(Collectors.joining())).
thenAccept(System.out::println).
join()

// prints "love"

/exit
```

> Note the `.` (dot) on some ends of lines. These are necessary so that the_JShell_ parser knows to continue to parse the expression over multiple lines.

_JShell_ seems to interfere a little in regards to standard streams.

## Ideas

- DSL over:
  - `java.lang.Process`
  - `java.util.spi.ToolProvider`
  - `java.nio.file.Path`
  - `java.nio.file.WatchService`
  - `java.nio.file.Files`?
  - `java.util.Stream`
  - `java.util.Map`
  - `java.util.List`
  - Pipelines
- functions == processes == commands
- Command (`java.lang.Process`)
  - Name (`String`)
  - Arguments (`String[]`)
  - Standard Input (`InputStream`)
  - Standard Output (`PrintStream`)
  - Standard Error (`PrintStream`)
  - Working Directory (`Path`)
  - Environment (`Map<String, String>`)
