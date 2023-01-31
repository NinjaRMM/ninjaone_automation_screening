#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
# Answers
# 1, it gets the virtual machines from Hyper-V host with name WPM
# 2, run a script based on a specified VM to check if there is a key under the windows registry and it prints if was found or not as an error.
# 3, run a script based on a specified VM to check if there is NinjaWPM.exe present in a specific folder and it prints if was found or not as an error.
#2.) What does the program assume you need to have access to in order to execute? 
# Answers
# 1, that the cmdlet is supported to run such powershell script.
# 2, that the VM name is found and in the same network mapping (it could be in another network). 
# 3, that the guest username and guest password are valid and correct to run the scripts under the VM.
# Remarks: The other validations like if windows registry and exe are found or not are handled as success or error.
#3.) What architectures can this script run on? 
# Answer
# Under Operational Systems that supports powershell. Besides windows, it can be configured in Linux and MacOS to make it work.

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