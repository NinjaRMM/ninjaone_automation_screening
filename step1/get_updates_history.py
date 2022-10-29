import subprocess
import platform


def search_for_particular_update(string_to_search):
    if get_updates_history().find(string_to_search) > -1:
        print("Update found")
    else:
        print("Update not found")


def get_updates_history() -> str:
    command = "uname"
    if platform.system() == "Linux":
        command = "cat /var/log/apt/history.log".split()
    elif platform.system() == "Darwin":
        command = "softwareupdate --history".split()
    elif platform.system() == "Windows":
        command = "wmic qfe list".split()
    else:
        print("It is not a Linux nor windows nor mac")

    return execute_command(command)


def execute_command(command):
    try:
        result_bytes_tuple = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT).communicate()
        return result_bytes_tuple[0].decode("utf-8")
    except Exception as e:
        print(e)


if __name__ == '__main__':
    print(get_updates_history())
    search_for_particular_update("Upgrade: linux-headers-generic-hwe-20.04")
