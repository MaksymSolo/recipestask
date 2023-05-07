# Recipes Book App Web Client

### Build and deploy
- Clone this repository to your local machine.
- Navigate to the recipesbook-client directory.
- Install the required dependencies by running **npm install**
- Build project by running **ng build**
- Start the development server by running **ng serve**

This will start the application on http://localhost:4200.

The application will automatically reload if you change any of the source files.

### Building with Docker
To build the project using Docker, run the following command from the project root directory:

**-docker build -t recipesbookapp_frontend .**
This will build a Docker image with the name recipesbookapp_frontend

**-docker run -t recipesbookapp_frontend**
This will start the application on http://localhost:8080
