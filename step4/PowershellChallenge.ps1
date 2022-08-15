#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing? 
# I left comments, but in general, it is checking if software is installed on a hyper-v VM.

#2.) What does the program assume you need to have access to in order to execute?
# It assumes you have access to a Windows Hyper-V environment.
# It assumes you will be running the script with access to an administrator account or an account with sufficient permissions.
# It assumes the user being used to invoke the script on the VM also is an administrator or an account with sufficient permissions.

#3.) What architectures can this script run on?
# This is a powershell script that calls other powershell scripts, so at the very least, both the host and VM must have powershell installed.
# It assumes that it will be run on a system with Windows Hyper-V enabled.
# It assumes that there is a VM by the name of WPM with powershell installed.

# get virtual machine by name WPM from hyper-v
$vm = Get-VM -Name WPM
# script to test if known ninjarmm registry key and folder exist indicating the software is installed
$IsNinjaInstalled = "Test-Path 'HKLM:\SOFTWARE\WOW6432Node\NinjaRMM LLC\NinjaWPM'"
# run script defined above inside of the VM named WPM using credentials provided
$Result = Invoke-VMScript -VM $vm -ScriptText $IsNinjaInstalled -GuestUser Ninja -GuestPassword helloworld
# exit the program with code 1 and an error message printed to stdout in red if the script ran above did not exit 0
$ExitCode = $Result.ExitCode
if($Result.ExitCode -ne "0") 
{
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    exit 1
}
# get stdout from script result, remove surrounding whitespace, and if == True, print message to stdout in green indicating software is installed
$output = ($Result.ScriptOutput).Trim();
if ($output -eq 'True') {
    Write-Host $vm NinjaWPM was found to be installed! Returned: $Result.ScriptOutput -ForegroundColor Green
}
# script to test if ninjaWPM is downloaded by testing if known file exists
$isNinjaWPMDownloaded = "Test-Path 'c:\NinjaInstaller\NinjaWPM.exe'"
# run script defined above insode of the VM named WPM using credentials provided
$Result = Invoke-VMScript -VM $vm -ScriptText $isNinjaWPMDownloaded -GuestUser Ninja -GuestPassword helloworld
# exit the program with code 1 and an error message printed to stdout in red if the script ran above did not exit 0
$ExitCode = $Result.ExitCode
if($Result.ExitCode -ne "0") 
{
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    exit 1
}
# if we get all the way to here, everything went well, and we exit 0
exit 0