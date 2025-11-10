# Usa uma imagem estável do Java 17 compatível com Render
FROM eclipse-temurin:17-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar gerado para dentro do container
COPY target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
