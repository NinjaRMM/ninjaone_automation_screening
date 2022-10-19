import subprocess
import platform
import pytest


@pytest.fixture(scope="module")
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

    yield execute_command(command)


def execute_command(command):
    try:
        result_bytes_tuple = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT).communicate()
        return result_bytes_tuple[0].decode("utf-8")
    except Exception as e:
        print(e)


def test_linux_updates(get_updates_history):
    if platform.system() != "Linux":
        pytest.skip("Incorrect OS: Linux")
    expected_string = "Upgrade: linux-headers-generic-hwe-20.04"
    assert get_updates_history.find(expected_string) > -1


def test_windows_updates(get_updates_history):
    if platform.system() != "Windows":
        pytest.skip("Incorrect OS: Windows")
    expected_string = "KB5015807"
    assert get_updates_history.find(expected_string) > -1


def test_mac_updates(get_updates_history):
    if platform.system() != "Darwin":
        pytest.skip("Incorrect OS: Mac")
    expected_string = "macOS Monterey 12.5"
    assert get_updates_history.find(expected_string) > -1
