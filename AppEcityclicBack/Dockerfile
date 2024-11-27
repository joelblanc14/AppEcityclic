FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw

CMD ["./mvnw", "spring-boot:run"]
