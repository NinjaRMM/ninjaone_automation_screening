#!/bin/bash

./gradlew test
./gradlew run --args="'$1' '$2'"