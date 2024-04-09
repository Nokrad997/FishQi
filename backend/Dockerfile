FROM openjdk:11-jdk-slim as build

COPY . /app
 
ENTRYPOINT [ "mvn", "clean", "install" ]

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]