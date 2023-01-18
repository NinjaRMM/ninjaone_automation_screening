#NINJARMM Code Challenge
 
#Please review the below code and answer the following questions to the best of your ability: 

#1.) What is this code doing?

# The code uses credentials to log in the virtual machine "WPM" and check if there's a directory "NinjaWPM" in the path given.
# If it is, it outputs a message saying it was found, in green letters, meaning that the software "NinjaWPM" is installed.
# If it's not found, it will check if there's a installer for such software downloaded on the machine, and if it is,
# it will exit returning 0. Otherwise, it'll output an error message in red and exit with "1".

#2.) What does the program assume you need to have access to in order to execute? 

# It assumes I'm on a Windows OS, with PowerShell, that I have the credentials to access the VM named 'WPM' - with
# VMware Tools installed - and that I have read access to the folder containing the virtual machine and a
# Virtual Machine.Interaction.Console Interaction privilege.

#3.) What architectures can this script run on? 

# The script itself needs PowerShell (and therefore Windows) to run, but its "Invoke-VMScript" command can run on a
# virtual machine using VMware, with either Windows or Linux as the OS.

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
