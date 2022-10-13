# NINJARMM Code Challenge
 
# Please review the below code and answer the following questions to the best of your ability: 

# 1.) What is this code doing? 

# The code is not working and I let a command for each line. Please that a look.
# Still, what this code seems to be supossed to do is the following:

# 1 . The code checks if the Ninja application is installed on a target machine. 
# 2 . If it is not installed, it trigers a script to download the installer.  
# 3 . The code than checks if the download was successful.
# 4 . If it wasn't, it displays an error message.
# 5 . If it was successful, it installs the application in the machine.
# 6 . If the installation was successful it exits with code 0.
# 7 . If the installation failed, it displays an error message and exits with code 1.


# 2.) What does the program assume you need to have access to in order to execute? 

# Access to the virtual machines.

# 3.) What architectures can this script run on? 

# 64bit Windows.

# Command to get the virtual machine from one or more Hyper-V hosts. 
$vm = Get-VM -Name WPM

# Returns True if the path exist, and False otherwise. 
$IsNinjaInstalled = "Test-Path 'HKLM:\SOFTWARE\WOW6432Node\NinjaRMM LLC\NinjaWPM'" 

# Invokes a script to run in the guest of the target VM. The -ScriptText parameter will receive a boolean
# value while it should receive a string to the script path or the script itself.
# Retuns a VMScriptResult object.
$Result = Invoke-VMScript -VM $vm -ScriptText $IsNinjaInstalled -GuestUser Ninja -GuestPassword helloworld

# Gets the Exit Code from the VMScriptResult object (Int).
$ExitCode = $Result.ExitCode

# Verifies if the exit code is diffenrent from "0".
# No metter what it the exit code this 'if' will be true because the exit ExitCode is a integer not a string.
if($Result.ExitCode -ne "0") 
{
    # I´m not sure if this command will execute because $vm is of type VirtualMachine object. But if so, 
    # this command will display in red something like: "<vm> Critical error on Test-Path: <exit-code>"
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red

    # Exits the scrip with exit code 1.
    exit 1
}

# ------------------------------------ The above code is never reached ------------------------------------

# Assuming this code is executed #

# Gets the Script Output from the VMScriptResult object (String), and Trims it.
$output = ($Result.ScriptOutput).Trim();

# Executed if the output is equal to 'True'
# Note: The flow is not interrupeted in case of 'False'
if ($output -eq 'True') {

    # I´m not sure if this command will execute because $vm is of type VirtualMachine object. But if so, 
    # this command will display in green something like: 
    # "<vm> NinjaWPM was found to be installed! Returned: <ScriptOutput>"
    # Note: The content of $Result.ScriptOutput was trimmed and saved to the variable $output in line 37.
    # Why not reuse? The untrimmed content may break the Write-Host command?
    Write-Host $vm NinjaWPM was found to be installed! Returned: $Result.ScriptOutput -ForegroundColor Green
}

# Test if the Path to the NinjeWPM installer exists. (redundant?)
$isNinjaWPMDownloaded = "Test-Path 'c:\NinjaInstaller\NinjaWPM.exe'"

# Invokes a script to run in the guest of the target VM. The -ScriptText parameter will receive a boolean
# value while it should receive a string to the script path or the script itself.
# Retuns a VMScriptResult object.
$Result = Invoke-VMScript -VM $vm -ScriptText $isNinjaWPMDownloaded -GuestUser Ninja -GuestPassword helloworld

# Gets the Exit Code from the VMScriptResult object (Int).
$ExitCode = $Result.ExitCode

# Verifies if the exit code is diffenrent from "0".
# No metter what it the exit code this 'if' will be true because the exit ExitCode is a integer not a string.
if($Result.ExitCode -ne "0") 
{
    # I´m not sure if this command will execute because $vm is of type VirtualMachine object. But if so, 
    # this command will display in red something like: "<vm> Critical error on Test-Path: <exit-code>"
    Write-Host $vm Critical error on Test-Path: $ExitCode -ForegroundColor Red

     # Exits the scrip with exit code 1.
    exit 1
}

# Exits the scrip with exit code 0.
exit 0