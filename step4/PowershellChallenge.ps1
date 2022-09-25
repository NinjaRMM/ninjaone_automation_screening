#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
<#This script first run the commands to check if a key exist in the Reg Key on a VM called WPM. This key is necessary to install NinjaRMM.
Then check if the installer is on the same VM.#>
#2.) What does the program assume you need to have access to in order to execute?
<#
* Read access to the folder containing the VM.
* Virtual Machine.Interaction.Console Interaction privilege.
* The virtual machines must be powered on and have VMware Tools installed.
* Network connectivity to the ESX system hosting the virtual machine on port 902.
* The guest account you use to authenticate with the guest operating system must have administrator's privileges
#>
#3.) What architectures can this script run on? 
# Due the reg key path use WOW6432Node it only runs on a 64 bits architecture.

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