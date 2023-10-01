FROM eclipse-temurin:17-jdk-jammy
LABEL org.opencontainers.image.authors="Nocne ptagi"
RUN apt -y update && apt -y install python3-pip

COPY model/requirements.txt .
RUN pip install -r requirements.txt && \
    rm requirements.txt

COPY ttydbe ttydbe
RUN sed -i 's/\r$//' ttydbe/mvnw && \
    chmod a+x ttydbe/mvnw && \
    ttydbe/mvnw -Dmaven.repo.local=ttydbe/.m2 -f ttydbe/pom.xml clean package && \
    cp ttydbe/target/ttydbe-0.0.1-SNAPSHOT.jar . && \
    rm -rf ttydbe

COPY model model

RUN mkdir /data
RUN touch /target.db

ENTRYPOINT ["java", "-jar", "/ttydbe-0.0.1-SNAPSHOT.jar"]
