# Usa uma imagem oficial do Maven com Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . .

# Baixa as dependências antes da execução para acelerar builds futuros
RUN mvn dependency:go-offline

# Compila o projeto (opcional, pois o comando final pode rodar diretamente os testes)
RUN mvn clean package -DskipTests

CMD ["mvn", "test"]
