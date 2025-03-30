FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia apenas o pom.xml primeiro (para cache de dependências)
COPY pom.xml ./
RUN mvn dependency:go-offline

# Agora copia o código-fonte
COPY . .

# Compila o projeto
RUN mvn clean package

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app /app
CMD ["mvn", "test"]
