## Step 2 Answer Overview

This solution was made with Java 11 and implement Gradle.
- To compile the project, execute the command `./gradlew clean build` in /src/StepTwoAnswer .
- The shell file is in `/src/StepTwoAnswer/step2answer.sh` and write first the decade, then the name of the output file, ex: 
    `./step2answer.sh "80" "80s-movies.json" `

## About cucumber test
- To run a cucumber test, add the file in /src/StepTwoAnswer/src/test/resources/features/ and execute the command `./gradlew cucumberCli`.
- The cucumber report can be found in folder /src/StepTwoAnswer/target


