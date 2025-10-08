# ETAPA 1: BUILDER - Compila o código dentro de um ambiente limpo com JDK 21
FROM eclipse-temurin:21-jdk-alpine AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código-fonte
COPY pom.xml .
COPY src ./src

# Executa o build do Maven. O Maven e o JDK 21 estão disponíveis aqui.
RUN ./mvnw clean package -DskipTests

# ETAPA 2: FINAL - Cria a imagem de execução leve
# Usa uma imagem JRE (Runtime Environment) mais leve e segura
FROM eclipse-temurin:21-jre-alpine AS final

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR compilado da etapa 'builder'
COPY --from=builder /app/target/Calculadora-web-0.0.1-SNAPSHOT.jar app.jar

# Define o ponto de entrada para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]