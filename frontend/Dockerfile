# Use Node.js Alpine image as base
FROM node:alpine

# Set working directory in the container
WORKDIR /app

# Copy package.json and pnpm-lock.yaml files
COPY package.json pnpm-lock.yaml ./

# Install dependencies using pnpm
RUN npm install -g pnpm && \
    pnpm install

RUN apk add --no-cache xdg-utils
# Copy all files from the current directory to the working directory
COPY . .

# Expose port 5173
EXPOSE 5173

# Command to run the application
CMD ["pnpm", "run", "dev"]
