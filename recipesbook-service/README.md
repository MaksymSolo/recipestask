# Recipe Book App REST Service
REST base API for "Recipe Book" application

### Prerequisites
To run this application, you will need:
- JDK 8 or above
- Maven

### Build and deploy
- Clone this repository to your local machine.
- Navigate to the recipesbook-service directory.
- Build and start the application by running **mvn spring-boot:run**

- This will start the backend application on http://localhost:8081.

### Building with Docker
To build the project using Docker, run the following command from the project root directory:

-**docker build -t recipesbookapp_backend .**
This will build a Docker image with the name recipesbookapp_backend

-**docker run -t  recipesbookapp_backend**
This will start the application on http://localhost:8081

### API Endpoints
The following endpoints are available in the backend API:

- GET /api/recipes: Retrieves all recipes.
- GET /api/recipes/{id}: Retrieves a recipe by ID.
- POST /api/recipes: Creates a new recipe.
- PUT /api/recipes/{id}: Updates an existing recipe.
- DELETE /api/recipes/{id}: Deletes a recipe by ID.


