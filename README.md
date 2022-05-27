# JDK Glue

DSL/script language for combining JDK tools.

## Example

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
// same as
new ProcessBuilder("java").start()

jwebserver(
    "--bind-address", "127.0.0.1",
    "--port", 8080,
    "--directory", output_javadoc
)

// System tools ?

git(
    "git", "rev-list", "--count", "--first-parent", "HEAD"
)


```

- Can semicolons be omitted?

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
