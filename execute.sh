echo "Stopping all docker containers and removing volumes..."
docker compose -f docker-compose.yml down -v
echo "Stopped all docker containers and volumes removed."
echo "Starting to compile the module..."
mvn clean package
echo "Library Management module compiled."
echo "Starting to build the docker image.."
docker compose -f docker-compose.yml build
echo "Docker image built."
echo "Bring up the containers"
docker compose -f docker-compose.yml up