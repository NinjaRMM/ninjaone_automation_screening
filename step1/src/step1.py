import subprocess

def isWindowsUpdateEnable():
    print('Checkinig if the Windows Update is running:')
    result = subprocess.check_output(['sc', 'query', 'wuauserv'])
    if 'RUNNING' in str(result):
        print("Windows Update is Running.")
    else:
        print("Windows Update is not Running.")

def getWindowsUpdateHistory():
    print('Getting the Windows Update History:\n')
    result = subprocess.check_output(['wmic', 'qfe', 'list']).decode('utf-8')
    print(result)

def main():
    getWindowsUpdateHistory()
    isWindowsUpdateEnable()

if __name__ == '__main__':
   main()