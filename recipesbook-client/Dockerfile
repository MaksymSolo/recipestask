# Stage 1: Compile and Build angular codebase

# Use official node image as the base image
FROM node:latest as build

# Set working directory
WORKDIR /app

# Install dependencies
COPY package.json package-lock.json ./
RUN npm install -g @angular/cli
RUN npm install --save-dev @angular-devkit/build-angular

# Copy project files
COPY . .

# Build project
RUN ng build

# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:latest

# Copy the build output to replace the default nginx contents.
COPY --from=build /app/dist/recipesbook-client /usr/share/nginx/html

# Copy nginx configuration file
COPY default.conf /etc/nginx/default.conf

# Expose port for web server
EXPOSE 80

# Start nginx server
CMD ["nginx", "-g", "daemon off;"]
