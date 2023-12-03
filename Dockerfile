# Base Image
FROM openjdk:17 as base
# Create work dir
# This instructs Docker to use this path as the default location for all subsequent commands
WORKDIR /app
# Copy necessary Files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make maven wrapper executable
RUN chmod +x mvnw
# Download and Install Dependencies into the image
RUN ./mvnw dependency:resolve
# Copy src files
COPY src ./src

FROM base as service-testing
RUN ["./mvnw", "test"]

FROM base as development-image
CMD ["./mvnw", "spring-boot:run"]


