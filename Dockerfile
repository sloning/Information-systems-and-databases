FROM openjdk:11-jre-slim
COPY build/libs/ISBD.jar ISBD.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/ISBD.jar"]