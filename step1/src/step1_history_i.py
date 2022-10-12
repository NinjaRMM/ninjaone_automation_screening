import subprocess

def getWindowsUpdateHistory():
    print('Getting the Windows Update History:\n')
    result = subprocess.check_output(['wmic', 'qfe', 'list']).decode('utf-8')
    print(result)

def main():
    getWindowsUpdateHistory()

if __name__ == '__main__':
   main()