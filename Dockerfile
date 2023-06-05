ARG APP_INSIGHTS_AGENT_VERSION=3.4.13
FROM hmctspublic.azurecr.io/base/java:17-distroless

USER hmcts
COPY lib/applicationinsights.json /opt/app/
COPY build/libs/civil-ccd-case-migration.jar /opt/app/

EXPOSE 4999

ENV SPRING_APPLICATION_NAME="civil-ccd-case-migration-tool"
ENV IDAM_API_URL="http://localhost:5000"
ENV IDAM_CLIENT_ID="civil"
ENV IDAM_CLIENT_SECRET="OOOOOOOOOOOOOOOO"
ENV IDAM_CLIENT_REDIRECT_URI="https://localhost:9000/oauth2/callback"
ENV CORE_CASE_DATA_API_URL="http://localhost:4452"
ENV IDAM_USERNAME="civil-system-update@mailnesia.com"
ENV IDAM_PASSWORD="Password12!"
ENV MIGRATION_JURISDICTION="Civil"
ENV MIGRATION_DRY_RUN="false"
ENV MIGRATION_ID="ResendRPAContinuousFeed"
ENV MIGRATION_CASE_TYPE="CIVIL"
ENV MIGRATION_EVENT_ID="NOTIFY_RPA_ON_CONTINUOUS_FEED"
ENV MIGRATION_EVENT_DESCRIPTION="rerunning RPA continuous feed"
ENV MIGRATION_EVENT_SUMMARY="rerunning RPA continuous feed"
ENV LOGGING_LEVEL_ROOT="ERROR"
ENV LOGGING_LEVEL_UK_GOV_HMCTS_REFORM="INFO"

CMD [ "civil-ccd-case-migration.jar" ]
