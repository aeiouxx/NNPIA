FROM node:16-alpine
WORKDIR /app
copy packacge*.json ./
RUN npm install
COPY . .
RUN npm run build
RUN npm install -g serve
expose 5000
CMD ["serve", "-s", "build", "-l", "5000"]
