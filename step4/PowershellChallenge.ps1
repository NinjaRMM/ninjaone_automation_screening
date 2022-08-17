#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
# It gets a Virtual Machine, called WPM.
# Then it tests if the files NinjaRMM and NinjaWPM exists on the defined folders.
# Then it runs the scripts on the VM and gets the result.
# In the case the script returned 0, it shows a critical error in red. And ends the execution.
# Otherwise it shows the result of the script execution in green telling that the script was found and executed.
# Then it verifies if the NinjaWPM.exe exists and execute it on the VM passing the username and password.
# If the result code is 0 then it shows a critical error message in red.
# Otherwise it just finish returning 0 to the caller.

#2.) What does the program assume you need to have access to in order to execute? 
# The user needs to have read privilege to open the VM and the user of the VM needs to have privilege to run Scripts, as a Admin problably.

#3.) What architectures can this script run on? 
# Windows x86 and 64

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