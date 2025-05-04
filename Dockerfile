# Rasm sifatida OpenJDK 17'ni ishlatamiz
FROM openjdk:17-jdk-slim

# App JAR faylini konteyner ichiga nusxalaymiz
COPY target/app.jar app.jar

# JAR faylni ishga tushirish
ENTRYPOINT ["java", "-jar", "/app.jar"]
