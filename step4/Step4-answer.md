## Please review the below code and answer the following questions to the best of your ability:

### 1.) What is this code doing?

+ The scripts starts in line 9 assigning a virtual machine into the variable $vm.
+ In line 10, it checks if the apps: NinjaRMM & NinjaWPM are installed and assign it to the variable $IsNinjaInstalled
+ In line 11 uses the previous result as a parameter to invoke a script in the virtual machine with an user & password and assigns the result into the variable $Result
+ Lines 12-17 checks if the exit code from the previous step is not zero. If it is not zero, displays an error message in the virtual machine with red font and the script ends with error. If the exit code is zero, the script continues to the following lines.
+ Lines 19-22 check the field "ScriptOutput". If it is equal to "True", displays an information sentence in the virtual machine with green font.
+ Line 24 checks the existence of a file and it is assigned to variable $isNinjaWPMDownloaded
+ Line 25 use the previous result as a parameter to invoke a script in the virtual machine with an user & password
+ Lines 26-31 checks the result of the script. If it is not zero, it displays an error message on the virtual machine with red font. Otherwise it continues.
+ Line 33 ends the script with 0, which means no errors.

### 2.) What does the program assume you need to have access to in order to execute?

+ The user and password provided are valid credentials in the virtual machine.
And this user has execution permissions to successfully execute the command Invoke-VMScript

### 3.) What architectures can this script run on?

+ Windows
