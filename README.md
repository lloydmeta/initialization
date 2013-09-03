initialization
==============

A Scala compiler plugin. Detects code that may run into
any of several Scala's initialization order
[pitfalls](https://github.com/paulp/scala-faq/wiki/Initialization-Order).
It does not pretend to prevent every reference-before-initialization.
Still, at least some classes of such errors can be covered.

The difference between this and `-Xcheckinit` compiler switch is that
checks are done purely during compilation time. There are no costs
incurred at runtime, although a library dependency is brought for
necessary metadata.

usage
-----

Add this to your `build.sbt`:

     autoCompilerPlugins := true

     addCompilerPlugin("com.github.pabzdzdzwiagief" %% "initialization" % "0.10.2")

     libraryDependencies += "com.github.pabzdzdzwiagief.initialization" %% "annotation" % "0.10.2"

example
-------

    // simple.scala

    pacakge localhost

    class simple {
      m1()
      val v1 = 4

      def m1() {
        println(v1)
      }
    }

The code above will generate following warning:

    simple.scala:10: warning: value v1 is referenced before assignment
            at localhost.simple.m1(simple.scala:6)
            at localhost.simple.<init>(simple.scala:5)

        println(v1)
                ^

For more examples see `src/test/resources/positives/`.

limitations
-----------

#### false positives

The plugin may happen to be overreactive. In such cases use standard
`@unchecked` annotation.

    // uncheck.scala

    pacakge localhost

    import util.Random.{nextBoolean => iFeelLucky}

    class uncheck {
      (m1() : @unchecked) // no warnings
      val v1 = 4

      def m1() {
        println(if (iFeelLucky) 4 else v1)
      }
    }

#### false negatives

For now, the plugin does not track references done from inner classes.
This also affects anonymous functions (which are implemented using innner
classes) and `for` loops (which are implemented using anonymous functions).

license
-------

[BSD 2-Clause](http://opensource.org/licenses/BSD-2-Clause)
