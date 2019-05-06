hello-micronaut
===============

Messing around with Micronaut to get a sense of it.

Quickstart
----------

```
docker-compose up -d # (Optional) Start dependencies
./gradlew run
```

Features
--------

Some miscellaneous things I was looking for that worked out-of-the-box
as expected:

- Jackson serialization support for Kotlin data classes
- Normal Logback logging. (Nothing fancy here, no structured logging.)
- 

### Jaeger Tracing

Jaeger Tracing is enabled by default. I've provided
docker-compose file to start up a local Jaeger instance, with the UI
available at http://localhost:16686/. Note you'll need to hit an
endpoint before anything will show up in the UI.

Enabling this required turning it on in the configuration and adding
these dependencies:
    
```kotlin
compile("io.micronaut", "micronaut-tracing")
compile("io.jaegertracing", "jaeger-thrift", "0.31.0")
```

Traces are broken down by matched template (e.g., `GET /foo/{var}`.)