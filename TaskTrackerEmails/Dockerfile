FROM jelastic/maven:3.9.5-openjdk-21 as builder
WORKDIR /appEmail
COPY . /appEmail/.
RUN mvn -f /appEmail/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /appEmail
COPY --from=builder /appEmail/target/*.jar /appEmail/*.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "/appEmail/*.jar"]