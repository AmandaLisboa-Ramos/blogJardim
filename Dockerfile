# Usa uma imagem do Java 17
FROM openjdk:17-jdk-slim

# Copia o jar gerado para dentro do container
COPY target/*.jar app.jar

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "/app.jar"]