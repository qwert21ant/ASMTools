# ApplySig

A command-line tool that applies generic type signature mappings to compiled Java classes inside a JAR file using the [ASM](https://asm.ow2.io/) bytecode manipulation library.

## What It Does

Java class files store generic type information in "signature" attributes — separate from the runtime bytecode. ApplySig reads a mapping file and rewrites those signatures across all classes in a JAR, leaving everything else (bytecode, non-mapped signatures, resources) untouched.

## Usage

```bash
java -cp applysig.jar ru.qwert21.tools.asm.Main <input.jar> <output.jar> <signatures.sig>
```

| Argument | Description |
|---|---|
| `input.jar` | Source JAR whose classes will be transformed |
| `output.jar` | Destination JAR with updated signatures |
| `signatures.sig` | Mapping file (see format below) |

## Signature File Format

Each line maps an element to its new signature. Three entry types are supported:

```
CL: <classname> <signature>
FD: <classname>/<fieldname> <signature>
MD: <classname>/<methodname> <signature>
```

Names use slash-separated JVM internal form. Example:

```
CL: com/example/Foo Lcom/example/Foo<TT;>;
FD: com/example/Foo/items Ljava/util/List<Ljava/lang/String;>;
MD: com/example/Foo/getItems ()Ljava/util/List<Ljava/lang/String;>;
```

Elements without a mapping entry are left unchanged.

## Building

Requires JDK 8+ and Gradle.

```bash
./gradlew build
```

The output JAR is placed in `build/libs/`.

## Dependencies

- [ASM 5.1](https://asm.ow2.io/) — bytecode reading and writing
- [Guava 19.0](https://github.com/google/guava) — utilities

## Project Info

- **Group**: `ru.qwert21.tools`
- **Artifact**: `ASMTools`
- **Version**: `0.1-SNAPSHOT`