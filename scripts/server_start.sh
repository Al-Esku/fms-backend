#!/usr/bin/env bash
cd /home/ec2-user/server || exit

source .env

export DB_URL
export DB_USERNAME
export DB_PASSWORD
export MAPBOX_KEY

sudo java -jar *.jar \
  --spring.config.additional-location=classpath:/production.properties > /dev/null 2> /dev/null < /dev/null &