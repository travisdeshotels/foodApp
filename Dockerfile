FROM maven:3.9.5-amazoncorretto-17-debian AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN java -version
RUN mvn -e -f /usr/src/app/pom.xml clean package

FROM amazoncorretto:17-alpine3.18
COPY --from=build /usr/src/app/target/food-0.0.1-SNAPSHOT.jar /usr/app/food-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/food-0.0.1-SNAPSHOT.jar"]
