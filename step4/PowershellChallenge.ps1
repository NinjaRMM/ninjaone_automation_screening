#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
     R/
      First: all gets a virtual machine with the name WPM check and it is saved on a variable
      Second: in a other variable the command to check if ninja software is installed on any computer (NinjaRMM,NinjaWPM) is saved
      Third: execute the previous command on a virtual machine with name WPM if the result of previous code is not zero display error
      the script end with error else the scrip continues.
      Four: If result is true it will write "NinjaWPM was found to be installed" and finally
      execute the NinjaWPM.exe and validate the answer


#2.) What does the program assume you need to have access to in order to execute?
     R/ The user and password should be valid access and Admin privileges on OS for execute the command Invoke-VMScript.
#3.) What architectures can this script run on?
     R/ Windows machines and thinking in automations tasks these type of script can be useful in Serverless Architectures
     It program would be useful for automate tasks related with installations of any application
     on a set of servers

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