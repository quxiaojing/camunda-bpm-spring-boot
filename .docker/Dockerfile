FROM dockerfile/java:oracle-java7
MAINTAINER Christian Lipphardt <christian.lipphardt@googlemail.com>

EXPOSE 8080

ADD ../camunda-bpm-spring-boot-example/target/spring-boot-camunda-bpm-example.jar /data/spring-boot-camunda-bpm-example.jar

CMD java -jar spring-boot-camunda-bpm-example.jar
