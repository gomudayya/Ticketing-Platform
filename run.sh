docker-compose -f docker-compose-infra.yml up -d

echo "Build discovery-server"
./gradlew :discovery-server:clean :discovery-server:build

echo "Build api-gateway"
./gradlew :api-gateway:clean :api-gateway:build

echo "Build user-service"
./gradlew :user-service:clean :user-service:build

echo "Build show-service"
./gradlew :show-service:clean :show-service:build

echo "Build order-service"
./gradlew :order-service:clean :order-service:build

docker-compose -f docker-compose-app.yml up -d --build

#특정서비스만 실행
#docker-compose -f docker-compose-infra.yml up -d --build redis