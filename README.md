# camunda-bpm-spring-boot
Camunda BPM Spring Boot integration

## Getting started

When you want to get started with Camunda BPM Spring Boot, you just have to include the `spring-boot-starter-camunda`
artifact in your spring boot project.

```xml
<dependency>
  <groupId>org.camunda.bpm.extensions</groupId>
  <artifactId>spring-boot-starter-camunda-bpm</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

This will auto-configure the Process Engine with:
* the correct DataSource
* enable JPA support if spring-boot-starter-data-jpa ("javax.persistence.EntityManagerFactory") is on the classpath

### How to import the REST classes (instead the war file)

If you want to add the Camunda BPM Rest API, you have to add following dependency to your maven build.

```xml
<dependency>
  <groupId>org.camunda.bpm</groupId>
  <artifactId>camunda-engine-rest</artifactId>
  <classifier>classes</classifier>
  <version>7.2.0</version>
</dependency>
```

If it is present on the classpath, it will be autodetected and made available under <url>.

### example with spring

* [embedded-spring-rest](https://github.com/camunda/camunda-bpm-examples/tree/master/deployment/embedded-spring-rest)
* http://docs.camunda.org/latest/guides/user-guide/#process-engine-process-engine-bootstrapping

## Maintainer

* [Christian Lipphardt](https://github.com/hawky-4s-), [camunda services GmbH](http://www.camunda.com/)

## License

* [Apache License, Version 2.0](./LICENSE)

## TODO

* Configuration
* Spring Actuator Endpoint for Process Engine
* Rest API security
* Configure ProcessEnginePlugins
* Make it possible to add the Camunda WebApp
