FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN ./gradlew bootJar
COPY app/build/lib/* build/lib/
COPY app/build/libs/backend-0.0.1-SNAPSHOT.jar build/

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app/build
ENV SPRING_PROFILES_ACTIVE docker
ENTRYPOINT java -jar backend-0.0.1-SNAPSHOT.jar