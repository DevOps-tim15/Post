FROM kaca97/maven-repo:0.1.0 AS appProductBuild
#FROM maven:3.6.3-ibmjava-8-alpine AS appProductBuild
ARG STAGE=dev
WORKDIR /usr/src/post-service
COPY . .
RUN mvn package -P${STAGE} -DskipTests
RUN mvn -B clean verify -Dspring.profiles.active=test

FROM openjdk:11.0-jdk AS appServerRuntime
WORKDIR /app
COPY --from=appProductBuild /usr/src/post-service/target/post.jar ./
EXPOSE 8080
CMD java -jar post.jar