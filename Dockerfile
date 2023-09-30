FROM eclipse-temurin:17-jdk-jammy
LABEL org.opencontainers.image.authors="Nocne ptagi"
COPY ttydbe ttydbe
RUN sed -i 's/\r$//' ttydbe/mvnw && \
    chmod a+x ttydbe/mvnw && \
    ttydbe/mvnw -Dmaven.repo.local=ttydbe/.m2 -f ttydbe/pom.xml clean package && \
    cp ttydbe/target/ttydbe-0.0.1-SNAPSHOT.jar . && \
    rm -rf ttydbe
ENTRYPOINT ["java", "-jar", "/ttydbe-0.0.1-SNAPSHOT.jar"]
