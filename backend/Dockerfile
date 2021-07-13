FROM azul/zulu-openjdk-alpine:8

ENV APP=/home/app/app.jar

COPY target/*.jar $APP

EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -jar $APP" ]