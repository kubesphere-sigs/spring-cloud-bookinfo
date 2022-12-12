FROM openjdk:8-alpine3.9
WORKDIR /app
ARG TARGET=reviews
COPY ${TARGET}/build/libs/ /app

ARG enable_ratings
ARG star_color
ENV ENABLE_RATINGS ${enable_ratings:-false}
ENV STAR_COLOR ${star_color:-black}

ENV TZ Asia/Shanghai
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]