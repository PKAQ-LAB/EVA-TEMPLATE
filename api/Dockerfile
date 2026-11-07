FROM eclipse-temurin:25-jre-alpine
MAINTAINER W.F # pkaq@msn.com
VOLUME /tmp
COPY *.jar /opt/app.jar
EXPOSE 9016
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app.jar"]
