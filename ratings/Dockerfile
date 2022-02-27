FROM golang:1.16 as builder
COPY / /app/builder
WORKDIR /app/builder
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -a -o app main.go

FROM gcr.io/distroless/static:nonroot
WORKDIR /
COPY --from=builder app/builder/app .
USER 65532:65532
ENTRYPOINT ["/app"]