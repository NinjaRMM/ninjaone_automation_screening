## Compiling the code
To compile java classes you can use javac compiler in a console like PowerShell like this
`javac -classpath step2/libs/json-20220924.jar com.step2.FilterMovies.java`

## Running the app
The app is a command line application, so to run it you can use a console like PowerShell and java command like this:
`java -cp step2/libs/json-20220924.jar;. com.step2.FilterMovies`

## Running the tests
The tests were made using cucumber 6.8.1 and junit 4.13, so to run the steps features, you can use the dependencies in the folder step2/libs or using build automation tool like gradle to configure the dependencies and to run the tasks

The sources of the tests is in the class com.step2.FilterMoviesByDecadeSteps

The runner class is the TestStep2Runner