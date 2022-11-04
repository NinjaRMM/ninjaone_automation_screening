#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?
#2.) What does the program assume you need to have access to in order to execute? 
#3.) What architectures can this script run on?

# I was not working whit such files before
# I see that is PowerShell script
# As I understand this code make two requests
# First check is Ninja software installed
# if response negative - finish code
# if positive make another request with installation NinjaWPM.exe file


# request of virtual machine mame
$vm = Get-VM -Name WPM
# string with request
$IsNinjaInstalled = "Test-Path 'HKLM:\SOFTWARE\WOW6432Node\NinjaRMM LLC\NinjaWPM'"
# make request with parameters, userName and password
$Result = Invoke-VMScript -VM $vm -ScriptText $IsNinjaInstalled -GuestUser Ninja -GuestPassword helloworld
# save exit code of response
$ExitCode = $Result.ExitCode
if($Result.ExitCode -ne "0") 
{
#if response code 0 throw error with red colour
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    # finish code execute
    exit 1
}

#save response to variable
$output = ($Result.ScriptOutput).Trim();
if ($output -eq 'True') {
#if response true return message with green colour
    Write-Host $vm NinjaWPM was found to be installed! Returned: $Result.ScriptOutput -ForegroundColor Green
}

#String variable with new request
$isNinjaWPMDownloaded = "Test-Path 'c:\NinjaInstaller\NinjaWPM.exe'"
# request with parameters user and password
$Result = Invoke-VMScript -VM $vm -ScriptText $isNinjaWPMDownloaded -GuestUser Ninja -GuestPassword helloworld
# saving response code to variable
$ExitCode = $Result.ExitCode
if($Result.ExitCode -ne "0") 
{
#if exit code 0 throw error red colour
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red
    # exit from code
    exit 1
}

# exit without errors
exit 0