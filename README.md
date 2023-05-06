# Recipe Book App
This is a web application for managing recipes. It consists of two parts: a frontend built with Angular, and a backend REST API built with Spring Boot.

### Prerequisites
Before running the application, make sure you have the following software installed:

- Docker
- Node.js 
- AngularCLI
- Maven
- JDK (version 8 or later)

### Building and Running the Application with Docker
To build and run the application using Docker, follow these steps:

- Clone this repository to your local machine.
- Navigate to the project root directory.
- Build the frontend Docker image by running **docker build -t recipesbookapp_frontend** . (note the trailing period).
- Build the backend Docker image by running **docker build -t recipesbookapp_backend** . (note the trailing period).
- **Or skip prev 2 steps** and build the backend Docker the beckend and frontend by **running docker-compose build**.
- Start the application using Docker Compose by running docker-compose up. This will start the frontend and backend containers, and link them together.
-Once the application is running, you can access it at http://localhost:4200.
Building and Running the Application Separately
- If you prefer to build and run the frontend and backend separately, follow these steps:

### Frontend
- Clone this repository to your local machine.
- Navigate to the recipesbook-client directory.
- Install the required dependencies by running npm install.
- Start the development server by running ng serve. 

This will start the frontend application on http://localhost:4200.

### Backend
- Clone this repository to your local machine.
- Navigate to the recipesbook-service directory.
- Build and start the application by running mvn spring-boot:run

- This will start the backend application on http://localhost:8080.

### API Endpoints
The following endpoints are available in the backend API:

- GET /api/recipes: Retrieves all recipes.
- GET /api/recipes/{id}: Retrieves a recipe by ID.
- POST /api/recipes: Creates a new recipe.
- PUT /api/recipes/{id}: Updates an existing recipe.
- DELETE /api/recipes/{id}: Deletes a recipe by ID.

