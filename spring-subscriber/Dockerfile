# Build aşaması
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# pom.xml'i kopyala ve bağımlılıkları indir (cache için)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Kaynak kodları kopyala ve build et
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime aşaması
FROM eclipse-temurin:21-jre
WORKDIR /app

# Build aşamasından JAR dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

# Port'u expose et (MQTT ve web için)
EXPOSE 8080

# Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]