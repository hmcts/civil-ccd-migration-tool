export SPRING_APPLICATION_NAME="civil-ccd-case-migration-tool"
export IDAM_API_URL="http://localhost:5000"
export IDAM_CLIENT_ID="civil"
export IDAM_CLIENT_SECRET="OOOOOOOOOOOOOOOO"
export IDAM_CLIENT_REDIRECT_URI="https://localhost:9000/oauth2/callback"
export CORE_CASE_DATA_API_URL="http://localhost:4452"
export IDAM_USERNAME="civil-system-update@mailnesia.com"
export IDAM_PASSWORD="Password12!"
export MIGRATION_JURISDICTION="Civil"
export MIGRATION_DRY_RUN="false"
export MIGRATION_ID="ResendRPAContinuousFeed"
export MIGRATION_CASE_TYPE="CIVIL"
export MIGRATION_EVENT_ID="NOTIFY_RPA_ON_CONTINUOUS_FEED"
export MIGRATION_EVENT_DESCRIPTION="rerunning RPA continuous feed"
export MIGRATION_EVENT_SUMMARY="rerunning RPA continuous feed"
export LOGGING_LEVEL_ROOT="ERROR"
export LOGGING_LEVEL_UK_GOV_HMCTS_REFORM="INFO"

java -jar build/libs/civil-ccd-case-migration.jar

docker build -t cccm:v1 .
docker images

docker run --name cccm -itd -P cccm:v1
docker logs cccm
docker ps -a
docker rmi -f $(docker images -aq)

docker exec -it cccm sh

