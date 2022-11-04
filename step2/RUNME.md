Code can be used in two ways:
1) Run from file with steps using IDE
    step2/src/test/resources/features/step-two-filter-movies.feature
2) Create artifact from module (.jar file) and run it from command line (picture attached Step2Jar.png)
       parameters:
      valid:
         -d 80 -o 80s.json
         -d 70 -o 70s.json
      invalid:
         -d 6 -o 66s.json      -> Error message "Invalid input value. Decade should be one of '20, 30, 40, 50, 60, 70, 80, 90'"


Decade 10 should be clarified because it can be two ranges or two output files
        1900-1910 and 2000-2010

                I delete movie "NinjaOne Easter Egg! - Delete this entire record from movies.json" from source file before run code.