# Civil CCD Data Migration Tool

## Basic overview

The basic premise of this tool is to be an implementation of [hmcts/ccd-case-migration-starter](https://github.com/hmcts/ccd-case-migration-starter).

It works by accessing the ccd-data-store-api as the system user, grabbing and filtering all cases, and then migrating the filtered cases.
To perform the migration there needs to be an event defined in the consuming case type that is defined with the ID `migrateCase`, this needs to be defined in CCD Definition.

This event is then triggered by the `CaseMigrationProcessor` defined in the [hmcts/ccd-case-migration-starter](https://github.com/hmcts/ccd-case-migration-starter),
and as it is a CCD event it can have the standard CCD hooks, i.e. `about-to-start`, `about-to-submit`, `submitted`.
Civil makes use of the `about-to-submit` hook to then perform the main part of migration.

### More info

For more info on the tool and how to use it check out [hmcts/ccd-case-migration-starter](https://github.com/hmcts/ccd-case-migration-starter)

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
-Dmigration.idam.username="cmc-system-update@mailnesia.com" \
-Dmigration.idam.password="[VALUE IN VAULT]" \
-Dmigration.jurisdiction="Civil" \
-Dmigration.caseType="[CASE TYPE]" \
-Dlogging.level.root="ERROR" \
-Dlogging.level.uk.gov.hmcts.reform="INFO" \
-Dfeign.client.config.default.connectTimeout="60000" \
-Dfeign.client.config.default.readTimeout="60000" \
PATH/TO/MIGRATION.jar
```

where

- `idam.client.secret`
- `idam.s2s-auth.totp_secret`
- `migration.idam.password`

can all be found in the relevant vault.

Note that the parameters given are using AAT environment as an example.

### Dependencies

With the deprecation of JCenter the dependencies

```groovy
compile group: 'uk.gov.hmcts.reform.ccd-case-migration', name: 'processor', version: '3.0.0'
compile group: 'uk.gov.hmcts.reform.ccd-case-migration', name: 'domain', version: '3.0.0'
```

are not available to be downloaded, to ensure that you can build and run migrations you can clone
this branch [stater repo](https://github.com/hmcts/ccd-case-migration-starter/) and compile the dependencies locally using

```shell
./gradlew clean build publishToMavenLocal
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

