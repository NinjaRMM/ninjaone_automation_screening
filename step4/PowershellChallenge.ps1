#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
# This code executes a powershell script inside of the VM "WPM" that checks if NinjaWPM is installed, using the given credentials.
# Then it returns a message saying if the software is installed or not.
# The last part executes the file located in 'c:\NinjaInstaller\NinjaWPM.exe', or returns an error if the software isn't installed.

#2.) What does the program assume you need to have access to in order to execute?
# It assumes you are on a windows machine, and that you have NinjaWPM software installed.

#3.) What architectures can this script run on?
# I don't know.

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