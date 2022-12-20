#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
#Answ: This code is checking for the presence of a software called NinjaWPM and a file called
#   "NinjaWPM.exe" on a virtual machine (VM) with the name "WPM". If the software or file is not found,
#   the script prints an error message and exits with a status code of 1. If the software is found and
#   the file is present, the script exits with a status code of 0.
#2.) What does the program assume you need to have access to in order to execute?
#Answ: In order to execute this script, the user must have access to a PowerShell session on the host system
#   where the VM is running, as well as access to the VM itself (including the guest username and password for the VM).
#3.) What architectures can this script run on?
#Answ: This script can run on any architecture that is supported by PowerShell, including x86 and x64.

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