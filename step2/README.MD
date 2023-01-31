## Background

automation-tool is the application used for the automation strategy for the exercise.

## Running the app

In the root folder, just run:

./step2.sh

## Expected result

In the end of the execution, the Cucumber Link report will be avaiable for consult like that:

? https://reports.cucumber.io/reports/<UUID> ?

The data folder will contain the "<range-data>-movies.json" generated side a side with the original "movies.json".

## Remarks

step-two-filter-movies.feature needed to be reformatted to work with latest version with Cucumber 7.x and Junit5 which is available under "automation-tool\src\test\resource\features" folder.