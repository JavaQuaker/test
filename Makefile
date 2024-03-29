setup:
	./gradlew wrapper --gradle-version 8.5

clean:
	./gradlew clean

build:
	./gradlew clean build

start:
	./gradlew bootRun --args='--spring.profiles.active=dev'

start-prod:
	./gradlew bootRun --args='--spring.profiles.active=prod'

install:
	./gradlew installDist

start-dist:
	./build/install/testWork/bin/testWork

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

check-updates:
	./gradlew dependencyUpdates

make api-doc:
	gradle clean generateOpenApiDocs

generate-migrations:
	./gradlew diffChangeLog

db-migrate:
	./gradlew update

.PHONY: build