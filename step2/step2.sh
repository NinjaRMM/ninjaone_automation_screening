#!/bin/bash

./gradlew test
./gradlew run --args="$*"