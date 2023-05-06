# Build Angular app
FROM node:16-alpine AS frontend
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Build Spring Boot app
FROM openjdk:11-jdk-slim AS backend
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean install

# Create final image
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=backend /app/target/recipesbook-service.jar .
COPY --from=frontend /app/dist/recipesbook-frontend /app
EXPOSE 8080
CMD ["java", "-jar", "recipesbook-service.jar"]