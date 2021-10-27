FROM golang:1.16-alpine3.14 as builder
COPY / /app/builder
WORKDIR /app/builder
RUN CGO_ENABLED=0 go build -trimpath -o app .

FROM alpine:3.14.2
COPY --from=builder /app/builder/app /app/
WORKDIR /app
CMD ["app"]