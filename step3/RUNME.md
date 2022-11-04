Code can be used in two ways:
1) Run from file with steps using IDE
    step3/src/test/resources/features/step-three-six-degrees.feature
2) Create artifact from module (.jar file) and run it from command line (picture attached Step3Jar.png)
       parameters:
      valid:
         "Tom Cruise"
         "Tom Cruise" "Sylvester Stallone"
      invalid:
         "Sylvester Stallone" "Some Actor"  -> Error message "Some Actor did not star in a movie in the data provided."

