# How to run get_updates_history.py script

We need to have python3 installed on your system. It can be windows, linux or mac.
The script query the OS for the OS Patches that are currently installed on the system.
Then we can run from a terminal:

```
$python get_updates_history

```

# How to validate the above program from an automation perspective

Here we can use pytest and store our tests in test_os_updates.py

+ As a requisite we need to install pytest:
```
$pip install pytest
```

+ Then we need to execute our tests defined in test_os_updates.py
```
$pytest test_os_updates.py
```
