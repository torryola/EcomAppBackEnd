# Base Image
FROM openjdk:17

# Create work dir
WORKDIR /app

# Copy necessary Files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install Dependencies
RUN ./mvnw dependency:resolve

# Copy src files
COPY src ./src

CMD ["./mvnw", "spring-boot:run"]


