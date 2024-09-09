# Stage 1: Build the application
FROM maven:3.8.1-openjdk-11 AS build
WORKDIR /ProductList
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create a lightweight image to run the application
FROM openjdk:11-jre-slim
WORKDIR /ProductList
COPY --from=build /ProductList/target/*.jar /ProductList/ProductList-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "ProductList-0.0.1-SNAPSHOT.jar"]
