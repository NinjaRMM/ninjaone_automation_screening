
## Compiling the code
To compile java classes you can use javac compiler in a console like PowerShell like this
`javac -classpath libs/gson-2.10.jar com.step3.SixDegrees.java`

## Running the app
The app is a command line application, so to run it you can use a console like PowerShell and java command like this:
`java -cp libs/gson-2.10.jar;. com.step3.SixDegrees`

## Running the tests
The tests were made using cucumber 6.8.1 and junit 4.13, so to run the steps features, you can use the dependencies in the folder step2/libs or using build automation tool like gradle to configure the dependencies and to run the tasks

The sources of the tests is in the class com.step3.FilterMoviesByDecadeSteps

The runner class is the TestStep3Runner class