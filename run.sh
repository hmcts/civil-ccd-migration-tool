java -jar \
	-Dspring.application.name="civil-ccd-case-migration-tool" \
	-Didam.api.url="http://localhost:5000" \
	-Didam.client.id="civil" \
	-Didam.client.secret="OOOOOOOOOOOOOOOO" \
	-Didam.client.redirect_uri="https://localhost:9000/oauth2/callback" \
	-Dcore_case_data.api.url="http://localhost:4452" \
	-Didam.username="civil-system-update@mailnesia.com" \
	-Didam.password="Password12!" \
	-Dmigration.jurisdiction="Civil" \
	-Dmigration.dryRun="false" \
	-Dmigration.id="ResendRPAContinuousFeed" \
	-Dmigration.caseType="CIVIL" \
	-Dmigration.eventId="NOTIFY_RPA_ON_CONTINUOUS_FEED" \
	-Dmigration.id="ResendRPAOfflineFeedNew1" \
	-Dmigration.eventDescription="rerunning RPA continuous feed" \
	-Dmigration.eventSummary="rerunning RPA continuous feed" \
	-Dlogging.level.root="ERROR" \
	-Dlogging.level.uk.gov.hmcts.reform="INFO" \
	build/libs/ccd-case-migration.jar
