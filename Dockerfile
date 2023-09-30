FROM openjdk:17
LABEL org.opencontainers.image.authors="Nocne ptagi"
COPY ttydbe ttydbe
WORKDIR ttydbe
RUN ./mvnw clean package
ENTRYPOINT ["java", "-jar", "/ttydbe/target/ttydbe-0.0.1-SNAPSHOT.jar"]
