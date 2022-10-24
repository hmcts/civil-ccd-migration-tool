# Civil CCD Data Migration Tool

## Basic overview

It works by accessing the ccd-data-store-api as the system user, grabbing and filtering all cases, and then migrating the filtered cases.
To perform the migration there needs to be an event defined in the consuming case type that is defined with the ID `migrateCase`, this needs to be defined in CCD Definition.

This event `migrateCase` is then triggered by the `CaseMigrationProcessor`and as it is a CCD event it can have the standard CCD hooks,
i.e. `about-to-start`, `about-to-submit`, `submitted`.
Civil makes use of the `about-to-submit` hook to then perform the main part of migration.

### Processor framework

The migration framework for data migrations within CCD that runs the following process:

![diagram](docs/process.png)

## Build

To build the project run

```shell
./gradlew clean build
```

this will generate a jar in the `build/libs` directory which can then be used when running the migration.

## Running

To run the jar you will need to do the following

```shell
java -jar \
-Dspring.application.name="civil-ccd-case-migration-tool" \
-Didam.api.url="https://idam-api.aat.platform.hmcts.net" \
-Didam.client.id="[CLIENT ID]" \
-Didam.client.secret="[VALUE IN VAULT]" \
-Didam.client.redirect_uri="https://civil-service-aat.service.core-compute-aat.internal/oauth2/callback" \
-Dcore_case_data.api.url="http://ccd-data-store-api-aat.service.core-compute-aat.internal" \
-Didam.s2s-auth.url="http://rpe-service-auth-provider-aat.service.core-compute-aat.internal" \
-Didam.s2s-auth.microservice="civil-service" \
-Didam.s2s-auth.totp_secret="[VALUE IN VAULT]" \
-Didam.username="cmc-system-update@mailnesia.com" \
-Didam.password="[VALUE IN VAULT]" \
-Dmigration.jurisdiction="Civil" \
-Dmigration.caseType="[CASE TYPE]" \
-Dmigration.esEnabled="[true/false]" \
-Dmigration.dryRun="[true/false]" \
-Dlogging.level.root="ERROR" \
-Dlogging.level.uk.gov.hmcts.reform="INFO" \
PATH/TO/MIGRATION.jar
```

where

- `idam.client.secret`
- `idam.s2s-auth.totp_secret`
- `migration.idam.password`

can all be found in the relevant vault.

Note that the parameters given are using AAT environment as an example.

NOTE: `migration.esEnabled` can be set to false for the environments where we don't have elastic search available. i.e. `local` or `preview`.
## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

