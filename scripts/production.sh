fuser -k 5000/tcp || true

source production/.env

export DB_URL
export DB_USERNAME
export DB_PASSWORD
export MAPBOX_KEY

java -jar production/libs/fencing-midsouth-api-0.0.1-SNAPSHOT.jar \
    --spring.config.additional-location=classpath:/production.properties