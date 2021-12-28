#!/usr/bin/env bash

./gradlew jib -Djib.to.image=registry.heroku.com/traintrckr/web -Djib.to.auth.username=_ -Djib.to.auth.password="$(heroku auth:token)"
heroku config:set SPRING_FLYWAY_ENABLED=true --app traintrckr && heroku container:release web --app traintrckr
sleep 60
heroku config:unset SPRING_FLYWAY_ENABLED --app traintrckr
