"""
Win32 commands to list Updates History and if automatic updates are active
"""
import os
import sys
import subprocess


def win32_os_update_history(html_format=False):
    """
    Returns os update history as string
    :param html_format:
    :return: str
    """
    cmd = ['powershell', 'wmic', 'qfe', 'list', 'full']

    if html_format:
        cmd += ['/format:htable']

    with subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE) as sb_process:
        stdout, _ = sb_process.communicate()

    return stdout.decode(sys.stdout.encoding)


def win32_automatic_windows_update_info():
    """
    Returns true if automatic updates are on
    Returns false if automatic updates are off
    :return: boolean
    """

    cmd = ['powershell', 'Get-ItemPropertyValue', '-Path',
           os.path.join("SOFTWARE",
                        "Policies",
                        "Microsoft",
                        "Windows",
                        "WindowsUpdate",
                        "AU"),
           '-Name', 'NoAutoUpdate']

    with subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE) as sb_process:
        stdout, _ = sb_process.communicate()

        if stdout.decode(sys.stdout.encoding).strip() == '1':
            return False

        return True
