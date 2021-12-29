FROM openjdk:11-jre-slim
COPY build/libs/ISBD.jar ISBD.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/ISBD.jar"]