import windows_tools.updates


def view_update_history():
    updates = windows_tools.updates.get_windows_updates()
    [print(f"Update: {i['title']}") for i in updates if i['title'] is not None]


view_update_history()
