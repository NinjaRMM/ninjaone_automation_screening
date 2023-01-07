#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

### *** My knowledge on shell script is very basic, I try my best. ***

#1.) What is this code doing?
After search Get-VM looks a command from Azure, you are geting a VM named WPM
Declare a variable $IsNinjaInstalled
Runs the command $IsNinjaInstalled on the VM called WPM with user and password
Capture the command result on $Result
Check if the $Result was not ok, if not stop the process.
Remove spaces before and after and set the result at $output
Verify if the $output value is true
Not sure what is Write-Host, looks a log
New variable $isNinjaWPMDownloaded declared
Runs the command $isNinjaWPMDownloaded on the VM called WPM with user and password
Check if the $Result was not ok, if not stop the process with error exit.
End the process with success.

#2.) What does the program assume you need to have access to in order to execute? 
You need to have access to the Get-VM and execute commands there.

#3.) What architectures can this script run on? 
Wndows

$vm = Get-VM -Name WPM
$IsNinjaInstalled = "Test-Path 'HKLM:\SOFTWARE\WOW6432Node\NinjaRMM LLC\NinjaWPM'"
$Result = Invoke-VMScript -VM $vm -ScriptText $IsNinjaInstalled -GuestUser Ninja -GuestPassword helloworld
$ExitCode = $Result.ExitCode
if($Result.ExitCode -ne "0") 
{
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    exit 1
}

$output = ($Result.ScriptOutput).Trim();
if ($output -eq 'True') {
    Write-Host $vm NinjaWPM was found to be installed! Returned: $Result.ScriptOutput -ForegroundColor Green
}

$isNinjaWPMDownloaded = "Test-Path 'c:\NinjaInstaller\NinjaWPM.exe'"
$Result = Invoke-VMScript -VM $vm -ScriptText $isNinjaWPMDownloaded -GuestUser Ninja -GuestPassword helloworld
$ExitCode = $Result.ExitCode
if($Result.ExitCode -ne "0") 
{
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    exit 1
}

exit 0