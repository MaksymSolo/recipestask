FROM node:lastest

# Set working directory
WORKDIR /app

# Install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy project files
COPY . .

# Build project
RUN npm run build

# Expose port for web server
EXPOSE 4200

# Run web server
CMD ["npm", "start"]
