#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?

# Check if path on Windows registry key for NinjaRMM and NinjaWPM are installed. To run those scripts on VM user Ninja and password helloword is used. If there is any error on
# script execution an error message will be showed and the execution will be terminated with code 1 (error). If there is no error the result will be checked and in case of true a message
# informing that NinjaWPM was found to be installed will be printed.
#
# After that the following command check if NinjaWPM.exe is downloaded in c:\NinjaInstaller folder. If there is any error the execution will be terminated with error
# indication, otherwise it will be finished successfully (code 0).

#2.) What does the program assume you need to have access to in order to execute? 

# The correct credentials and necessary permissions to run the script.


#3.) What architectures can this script run on? 

# x86-64 (Windows). The HKLM:\SOFTWARE\WOW6432Node key refers to 64-bits Windows version. It's used to present a separate view
# for 32-bits applications that run on a 64-bits version.

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