SET DOCKER_REPOSITORY=core
SET DOCKER_GOAL=dockerBuild
mvn clean package -U
docker-compose up