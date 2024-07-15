FROM    maven:3.9.6-eclipse-temurin-22
WORKDIR /.m2:/root/.m2
COPY .. .
RUN mvn clean install
CMD mvn spring-boot:run