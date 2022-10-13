import subprocess

# Comments:
# On my research I first went through 2 directions:
# 1. An API to interact through terminal.
# 2. Getting this information from the Windows Registry
#
# For the nunmber 1, I couldn´t find something easy to implement,
# while for number 2, I found results very dependend on the OS version.
# I realized that the policy to disable automatic updates are different
# from one Win version to another, with distict locations on the Registry.
#
# Due to that, I looked foward getting the status of the Windows Update service,
# because if it is not running, the automatic updates will not happen.

def isWindowsUpdateEnable():
    print('Checkinig if the Windows Update is running:')
    result = subprocess.check_output(['sc', 'query', 'wuauserv'])
    if 'RUNNING' in str(result):
        print("Windows Update is Running.")
    else:
        print("Windows Update is not Running.")

def main():
    isWindowsUpdateEnable()

if __name__ == '__main__':
   main()