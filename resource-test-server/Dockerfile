FROM openjdk:11
COPY ./target/resource-test-server-0.0.1-SNAPSHOT.jar resource-test-server.jar
EXPOSE 8080 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar","resource-test-server.jar"]
