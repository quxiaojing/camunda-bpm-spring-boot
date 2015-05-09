FROM dockerfile/java:oracle-java7
MAINTAINER Christian Lipphardt <christian.lipphardt@camunda.com>

ENV VERSION 1.0.0-SNAPSHOT

EXPOSE 8080

ADD ../examples/camunda-bpm-rest-spring-boot-example/target/camunda-bpm-rest-spring-boot-example-${VERSION}.jar /data/camunda-bpm-rest-spring-boot-example-${VERSION}.jar

CMD java -jar /data/camunda-bpm-rest-spring-boot-example-${VERSION}.jar
