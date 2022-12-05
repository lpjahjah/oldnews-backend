#FROM gradle:7.6-jdk17-alpine AS build
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build --no-daemon

#FROM eclipse-temurin:17-jre-alpine
#ENV SPRING_PROFILES_ACTIVE docker
#RUN mkdir /app
#COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
#ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]

FROM eclipse-temurin:17-jre-alpine
ENV SPRING_PROFILES_ACTIVE docker
WORKDIR /app
COPY build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]