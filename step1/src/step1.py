import windows_tools.updates


def get_updates():
    return windows_tools.updates.get_windows_updates(filter_duplicates=True, include_all_states=False)


if __name__ == "__main__":
    updates = get_updates()

    print("Total ammount of update is: ", len(updates))

    for update in updates:
        print(update)
