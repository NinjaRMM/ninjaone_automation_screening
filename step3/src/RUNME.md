install Java jdk 1.8u102 (jdk1.8.0_102), on Windows the default install path would be C:\Program Files\Java\jdk1.8.0_102

https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html
Ctrl+F Java SE Development Kit 8u102

check C:\Program Files\Java\jdk1.8.0_102\bin and ensure jjs.exe was installed
(other versions may still work if they have jjs.exe, but I only tested on this version)

Open a command prompt
(Optional) add jjs.exe to the PATH
(Optional) cd to this folder

Execute:

"C:/Program Files/Java/jdk1.8.0_102/bin/jjs" /path/to/ninjaone/ninjaone_automation_screening/step3/src/step3.js -- "Tom Cruise" "Sylvester Stallone"
jjs /path/to/ninjaone/ninjaone_automation_screening/step3/src/step3.js -- "Tom Cruise"
jjs /path/to/ninjaone/ninjaone_automation_screening/step3/src/step3.js
jjs step3.js -- "Buzz Lightyear"


example #1 assumes no optional was done
example #2-4 assumes jjs is on PATH
example #3 is not listed on Gherkin as a use case but it is implemented regardless
example #4 assumes the console is on this folder

do note -- must be added between the script and parameters, this is because jjs can take an array of scripts to execute



The solution was done through jjs to minimize dependencies (only the JDK), otherwise a JSON third party library would be needed.