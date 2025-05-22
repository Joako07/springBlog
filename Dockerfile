FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/springboot-blog-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} springblog.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springblog.jar"]