#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
# It is getting an active VM called WPM and is executing scripts on the virtual machine to validate if NinjaWPM is
# installed in the VM. At first it uses REGISTRY KEY to say if it is installed or not by writing into the host machine.
# In the second step it is validating if the NinjaWPM.exe is present on the specificied path and it warns if not by
# writing on the host machine.
#2.) What does the program assume you need to have access to in order to execute?
# It uses VM so it requires to have virtualization activated on the host machine also depending on how it is
# configured root access is needed for this script.
#3.) What architectures can this script run on?
# This script is based on win32 architecture.

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