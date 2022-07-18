#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
     It is trying to get the details of the NinjaRMM Software and giving back the details about the NinjaRMM installation.
#2.) What does the program assume you need to have access to in order to execute? 
     The program assumes to have the exit code as ForegroundColor to be green. 
#3.) What architectures can this script run on?
     It can run on any database platform like SQL.

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
