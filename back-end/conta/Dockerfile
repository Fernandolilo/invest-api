FROM eclipse-temurin:17-jdk
RUN apt-get update && apt-get -y upgrade && apt-get install -y curl
ARG JAR_FILE=target/com.invest-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar' 
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/.urandom","-jar","/app.jar"]