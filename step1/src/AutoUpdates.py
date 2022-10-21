import subprocess, sys
import platform

osSystem = str(platform.system())
if osSystem == "Windows":
    execution = subprocess.Popen(["powershell.exe",
              "./WINUpdatesTable.ps1"],
              stdout=sys.stdout)
    execution.communicate()

elif osSystem == "Linux":
    ##We could add diffents OS on this way