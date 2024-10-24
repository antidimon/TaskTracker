FROM jelastic/maven:3.9.5-openjdk-21 as builder
WORKDIR /appStats
COPY . /appStats/.
RUN mvn -f /appStats/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /appStats
COPY --from=builder /appStats/target/*.jar /appStats/*.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/appStats/*.jar"]