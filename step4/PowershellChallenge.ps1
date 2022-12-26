#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
# It's getting VM WPM and checking on it if the NinjaWPM is installed, checking at Windows Registry
# by triggering into VM the script ""Test-Path 'HKLM:\SOFTWARE\WOW6432Node\NinjaRMM LLC\NinjaWPM'" passed as argument with user ninja and password helloworld
# If the result is different than zero, then it prints "Critical error" in red
# Otherwise it reads the script output. If true, it prints that WPM was found and can be installed in green
# Afterwards it runs again on the VM a script to test if NinjaWPM.exe exists on path c:\NinjaInstaller\
# If the script's result is different than zero then it prints "Critical error" in red and exit the program with code 1
# Otherwise it exits the program with code 0

#2.) What does the program assume you need to have access to in order to execute? 
# The user must have permission to run PowerShell scripts on the Host
# The user Ninja must have permission to run commands on VM
# The user Ninja must have permission to access Windows Registry on VM
# the user Ninja must have permission to access VM's c: driver

#3.) What architectures can this script run on? 
# 32 and 64 bits

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