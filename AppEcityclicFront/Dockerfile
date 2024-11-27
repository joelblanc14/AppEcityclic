FROM node:20-slim

WORKDIR /app

COPY . .

RUN npm install -g @angular/cli
RUN npm install

EXPOSE 4200

CMD ["ng", "serve", "--host", "0.0.0.0"]
