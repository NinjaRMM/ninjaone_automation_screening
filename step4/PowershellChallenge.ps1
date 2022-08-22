#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
# Check in the code my comments
#2.) What does the program assume you need to have access to in order to execute? 
# Admin right ,  otherwise it will elevate and ask to run as admin or it will fail, since it is installing an executable
#3.) What architectures can this script run on? 
#Windows with powershell enable 

$vm = Get-VM -Name WPM
$IsNinjaInstalled = "Test-Path 'HKLM:\SOFTWARE\WOW6432Node\NinjaRMM LLC\NinjaWPM'"
$Result = Invoke-VMScript -VM $vm -ScriptText $IsNinjaInstalled -GuestUser Ninja -GuestPassword helloworld
$ExitCode = $Result.ExitCode
#1R IT get the VM name from the host , then invoque a script that check if it is installed with an non encripted username and password

if($Result.ExitCode -ne "0") 
{
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    exit 1
}
#if exit code then print this error 

$output = ($Result.ScriptOutput).Trim();
if ($output -eq 'True') {
    Write-Host $vm NinjaWPM was found to be installed! Returned: $Result.ScriptOutput -ForegroundColor Green
}
# Program is found (key ins regedit)

$isNinjaWPMDownloaded = "Test-Path 'c:\NinjaInstaller\NinjaWPM.exe'"
$Result = Invoke-VMScript -VM $vm -ScriptText $isNinjaWPMDownloaded -GuestUser Ninja -GuestPassword helloworld
$ExitCode = $Result.ExitCode
# Just run the installation
if($Result.ExitCode -ne "0") 
{
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    exit 1
}
# if it fails print this otherwise end it here

exit 0