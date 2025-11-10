# Etapa 1: Build da aplicação com Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e baixa dependências (isso melhora o cache)
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copia o restante do código-fonte
COPY src ./src

# Compila e empacota a aplicação (gera o JAR)
RUN mvn clean package -DskipTests

# Etapa 2: Executa a aplicação com Java 17
FROM eclipse-temurin:17-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
