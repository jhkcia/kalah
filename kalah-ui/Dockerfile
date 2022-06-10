FROM node:18-alpine AS builder

WORKDIR /opt/web
COPY package.json package-lock.json ./
RUN npm install

ENV PATH="./node_modules/.bin:$PATH"

COPY . ./
RUN npm run build

FROM nginx:1.21-alpine
RUN apk --no-cache add curl

COPY ./nginx.config /etc/nginx/conf.d/default.conf
CMD ["/bin/sh", "-c", "nginx -g 'daemon off;'"]
COPY --from=builder /opt/web/build /usr/share/nginx/html
