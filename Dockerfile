FROM openjdk:17-oracle
ADD target/core.jar core.jar
EXPOSE 8085
ENTRYPOINT ["java" , "-jar" , "core.jar"]
