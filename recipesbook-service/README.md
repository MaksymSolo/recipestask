# Recipe Book App REST Service
REST base API for "Recipe Book" application

### Prerequisites
To run this application, you will need:
- JDK 8 or above
- Maven

### Installation
Clone this repository to your local machine.

Navigate to the project root directory and run the following command:

- mvn spring-boot:run
- 
Once the application is running, you can access it at http://localhost:8080.

Usage
The following endpoints are available in this application:

- GET /api/recipes: Retrieves all recipes
- GET /api/recipes/{id}: Retrieves a recipe by ID
- POST /api/recipes: Creates a new recipe
- PUT /api/recipes/{id}: Updates an existing recipe
- DELETE /api/recipes/{id}: Deletes a recipe by ID

###Building with Docker
To build the project using Docker, run the following command from the project root directory:

-docker build -t recipesbookapp_backend 

This will build a Docker image with the name recipesbookapp_backend. To run the Docker container, use the following command:

-docker run -p 8080:8080 recipesbookapp_backend

This will start the container and map the host port 8080 to the container's port 8080. 

You can then access the application at http://localhost:8080.
