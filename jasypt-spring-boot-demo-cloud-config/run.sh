#!/usr/bin/env bash

set -e

YELLOW='\033[1;33m'
NC='\033[0m'

OPTIND=1
BUILD=false
CLEAN=false

while getopts "cb" opt; do
  case $opt in
    c)
      CLEAN=true
      ;;
    b)
      BUILD=true
      ;;
    *)
      ;;
  esac
done
shift $((OPTIND-1))

if [ "$CLEAN" = true ]; then
  echo -e "${YELLOW}Cleaning App${NC}"
  mvn clean
fi

if [ "$BUILD" = true ] || [ ! -f target/*.jar ]
then
    if [ ! -f target/*.jar ]; then
        echo -e "${YELLOW}JAR file not found, building app${NC}"
    else
        echo -e "${YELLOW}Building App${NC}"
    fi
    mvn clean install
fi
echo -e "${YELLOW}Killing Container(s)${NC}"
docker compose kill
echo -e "${YELLOW}Removing Container(s)${NC}"
docker compose rm -f
echo -e "${YELLOW}Building Image(s)${NC}"
docker build --build-arg JAR_FILE=target/*.jar -t jasypt-spring-boot-demo-cloud-config-client .
echo -e "${YELLOW}Running App${NC}"
docker compose run config-client
