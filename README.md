# Welcome!
Thank you for considering NinjaRMM as your future home. In this repository you'll find some of our screening materials that we expect you to complete. The folder structure is explained below.

Take your time when completing these test materials as accuracy is important. Feel free to use whatever reference materials you need in order to complete and answer all of the questions. Incomplete submissions will not be considered. You should begin by creating a branch that contains your name and start date as you'll need to submit this as a pull-request once you are done.

# Overview
The repository is broken down into individual steps we would like for you to complete. Each subfolder step1 -> step4 contains an individual piece of test material. We expect that the solution for each step will be contained as a subfolder, for example for step2 we ask you to write some basic code for us. Ideally you would structure that folder so that the source code is contained in a subfolder called "src" so {root}/step2/src/* would contain your answer. 

Please provide a {root}/{step#}/src/`RUNME.md` file for your source code that explains how to compile and execute your logic. 

You are free to use any coding language you are comfortable with.

## Step 1
The first step is to answer all of the questions in `CodingQuestions.txt` file in the `step1/` directory. Feel free to write any general answers within that document itself. We expect the small programming challenges to be delivered as an individual file(s). Use any reference materials that you need.

## Step 2
Read the `step-two-filter-movies.feature` file in the `step2/` directory. Given a large data set (all movies made from 1900-2018) `data/movies.json`, follow the [Gherkin/BDD](https://www.guru99.com/gherkin-test-cucumber.html) style requirements and write us an application that will implement them. The application can be written in any programming language, but we implore you to write it in a language you are most comfortable with so you can show off your skillsets. A commandline application and/or script is expected.

Example commandline application execution: ./my_movie_parser.sh -decade 80 --output "80s-movies.json"

Feel free to do the parameters as you see fit, the above is just an example.

## Step 3
Read the `step-three-six-degrees.feature` file in the `step3/` directory. Here we would like you to implement a [Six Degrees of Kevin Bacon](https://en.wikipedia.org/wiki/Six_Degrees_of_Kevin_Bacon#:~:text=Six%20Degrees%20of%20Kevin%20Bacon%20or%20Bacon's%20Law%20is%20a,ultimately%20leads%20to%20prolific%20American) implementation from requirements in the feature file. The application can be written in any programming language, but we implore you to write it in a language you are most comfortable with so you can show off your skillsets. A commandline application and/or script is expected.

Example execution set:

Example A:
Input: arr = ["Tom Cruise"]
Output: 2

Example B:
Input: arr = ["Tom Cruise", "Sylvester Stallone"]
Output: 2

Example C:
Input: arr = ["Buzz Lightyear"]
Output: "Buzz Lightyear did not star in a movie in the data provided."

## Step 4
Read the instructions at the top of the `PowershellChallenge.ps1` file in the `step4/` directory and answer accordingly.

# Submission
Once you feel you have everything in a good spot, please submit your answer as a pull-request against this repository and notify your contact that you are complete. We'll review it as soon as possible and get back to you.

Please make sure all of the following are checked:

1. All of the questions in step 1 have been answered.
2. The code for steps 2 and 3 compiles (if using a compiled language) and runs without errors.
3. Given the instructions you provide in the `RUNME.md`, we should be able to execute your solutions from steps 2 and 3 from the command line and have the results printed to the console if required in the step feature definition.

Happy coding!
