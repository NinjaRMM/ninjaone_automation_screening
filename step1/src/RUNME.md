drag and drop step1.ps1 in powershell and press enter

if the shell claims it lacks permissions to run scripts, try to use:
Set-ExecutionPolicy RemoteSigned -Scope Process

or simply copy the contents of the script, paste them into the shell, and press enter