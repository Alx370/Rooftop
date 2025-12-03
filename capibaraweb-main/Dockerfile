# ---- BUILD PHASE ----
FROM node:18-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install --quiet

COPY . .
RUN npm run build

# ---- SERVE PHASE ----
FROM nginx:alpine

# Copy custom nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built app
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
