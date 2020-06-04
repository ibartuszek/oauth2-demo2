FROM openjdk:11
# c:\Projects\oauth2-demo2\client-to-resource-server\target\client-to-resource-server-0.0.1-SNAPSHOT.jar
COPY ./target/client-to-resource-server-0.0.1-SNAPSHOT.jar client-to-resource-server.jar
EXPOSE 8080 5006
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006", "-jar","client-to-resource-server.jar"]
