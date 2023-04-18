FROM openjdk:20
COPY target/*.jar system_design.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","system_design.jar"]