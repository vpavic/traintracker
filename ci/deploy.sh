#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset
# set -o xtrace

# build Docker image and publish it to Heroku registry
./gradlew jib -Djib.to.image=registry.heroku.com/traintrckr/web -Djib.to.auth.username=_ -Djib.to.auth.password="$(heroku auth:token)"
# enable Flyway migrations
heroku config:set SPRING_FLYWAY_ENABLED=true --app traintrckr
# deploy application
heroku container:release web --app traintrckr || true
# wait for application to start up
sleep 60
# disable Flyway migrations
heroku config:unset SPRING_FLYWAY_ENABLED --app traintrckr
