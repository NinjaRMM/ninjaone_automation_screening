"""
Command line tools to get OS installed patches and verify if automatic updates are active
"""
import sys
import argparse
import webbrowser
import tempfile
import time

from win32.commands import win32_os_update_history, win32_automatic_windows_update_info


def parse_arguments():
    """
    Parse arguments
    :return:parsed arguments namespace
    """
    parser = argparse.ArgumentParser(description='Python tool for overall win32 actions')
    parser.add_argument('--open_browser', default=False,
                        action="store_true", help="Open Browser information for latest updates")

    parsed_args = parser.parse_args()
    return parsed_args


def open_browser(content):
    """
    Opens default browser with the given content
    :param content:str
    """
    with tempfile.NamedTemporaryFile(suffix='.html') as tmp_file:
        tmp_file.flush()

    with open(tmp_file.name, 'w', encoding='utf-8') as f_handler:
        f_handler.write(content)

    # Make sure that have a browser available
    webbrowser.get()
    webbrowser.open_new_tab(f"file://{tmp_file.name}")
    # Give some time to open browser
    time.sleep(1)


if __name__ == "__main__":
    args = parse_arguments()

    if sys.platform == "win32":
        os_update_history = win32_os_update_history(html_format=args.open_browser)
        automatic_updates = win32_automatic_windows_update_info()

        if args.open_browser:
            open_browser(f"</H3>"
                         f"<H4>Automatic Updates: {automatic_updates}</H4>".join(os_update_history
                                                                                 .split("</H3>")))
        else:
            print(os_update_history)
            print(f"Automatic Updates: {automatic_updates}")
    else:
        print("Not Supported")
