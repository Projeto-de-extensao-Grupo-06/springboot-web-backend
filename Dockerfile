# ── Estágio 1: Build ───────────────────────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de camadas do Docker.
# As dependências só são baixadas novamente se o pom.xml mudar.
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia o código-fonte e compila
COPY src ./src
RUN mvn package -DskipTests -q

# ── Estágio 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Usando variáveis de ambiente para o perfil ativo, permitindo fácil alteração em diferentes ambientes
ENV SPRING_PROFILES_ACTIVE=dev

ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]