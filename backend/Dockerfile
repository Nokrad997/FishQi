FROM maven:3.9-eclipse-temurin-21-alpine 

# Ustawienie katalogu roboczego
WORKDIR /app
RUN mkdir /temp

# Kopiowanie pliku pom.xml i pobieranie zależności
COPY pom.xml ./
RUN mvn dependency:go-offline

# Kopiowanie źródeł aplikacji
COPY src ./src

# Budowanie aplikacji
RUN mvn package

# Użycie lżejszego obrazu do uruchomienia aplikacji
FROM eclipse-temurin:21-jre-alpine

# Ustawienie katalogu roboczego
WORKDIR /app

# Kopiowanie zbudowanego artefaktu z poprzedniego etapu
COPY --from=0 /app/target/fishqi-0.0.1-SNAPSHOT.jar app.jar

# Uruchamianie aplikacji
CMD ["java", "-jar", "app.jar"]
