# NScheme

A small implementation of the Scheme (lisp based) language. Allows functionality for lambda defined functions, variables, and control operations.

## Building

NScheme is written in Java and is able to be compiled in all main Java IDE's. Since NScheme does not require any outside libraries, simply import the project into your IDE of choice and run the [SchemeCLI] class.

## How to use

See the [SchemeCLI] class to view an example of a command line application build using NScheme.

To evaluate a string of scheme code, the following example will return the object with the resulting evaluation:

``` Java
Object tokens = Parser.parse(line);
Object result = Language.eval(tokens, env);
```

[SchemeCLI]:https://github.com/binarii/NScheme/blob/master/src/com/nuke/scheme/cli/SchemeCLI.java
