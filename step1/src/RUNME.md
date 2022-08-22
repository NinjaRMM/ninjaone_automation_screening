I Used the basic powershell commands to get the information required
if you run the powershell you be asked to give the permision

Just run in a open powershell session
>.\step1.ps1

Set-ExecutionPolicy RemoteSigned
select A for ALL

Q: Query the OS for the OS Patches that are currently installed on the system. 
Here is example output from my machine :

Caption                                     CSName           Description      FixComments  HotFixID   InstallDate  InstalledBy            InstalledOn  Name  ServicePackInEffect  Status
http://support.microsoft.com/?kbid=5015730  DESKTOP-40CEG33  Update                        KB5015730               AUTORIDADE NT\SISTEMA  8/14/2022
http://support.microsoft.com/?kbid=4537759  DESKTOP-40CEG33  Security Update               KB4537759                                      5/11/2020
http://support.microsoft.com/?kbid=4557968  DESKTOP-40CEG33  Security Update               KB4557968                                      5/11/2020
http://support.microsoft.com/?kbid=4560366  DESKTOP-40CEG33  Security Update               KB4560366               AUTORIDADE NT\SISTEMA  6/24/2020


Q Add a function to report if Automatic Updates are enabled or disabled for the device.
For the update system your will see if the service is activated or not. 

Status   Name               DisplayName
------   ----               -----------
Stopped  wuauserv           Windows Update