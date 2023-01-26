#!/bin/bash

FILE=build/libs/step3-0.0.1-SNAPSHOT.jar
if ! [ -f "$FILE" ]; then
    ./gradlew build
fi

echo -e "\n\n"

java -jar "$FILE" "$@"
