FROM node:12.13.0-alpine as builder
COPY / /app/builder
WORKDIR /app/builder
RUN yarn && yarn build

FROM node:12.13.0-alpine
COPY --from=builder /app/builder/dist /app
WORKDIR /app

CMD ["node", "server.js"]