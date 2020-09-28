#!/usr/bin/env bash

echo "Enabling Liquibase migrations..."
heroku config:set SPRING_LIQUIBASE_ENABLED=true --app traintrckr
echo "Done! Waiting 30 seconds for the application to restage."

sleep 30

echo "Deploying the application..."
heroku container:release web --app traintrckr
echo "Done! Waiting 30 seconds for the application to restage."

sleep 30

echo "Disabling Liquibase migrations..."
heroku config:unset SPRING_LIQUIBASE_ENABLED --app traintrckr
echo "Done!"
