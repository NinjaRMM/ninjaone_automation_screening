from __future__ import print_function
from pywinauto import Application

Application().start('control.exe')
app = Application(backend='uia').connect(path='explorer.exe', title='Panel de control')

app.window(title='Panel de control').ProgramsHyperlink.invoke()
app.wait_cpu_usage_lower(threshold=0.5, timeout=30, usage_interval=1.0)

app.window(title='Programas').child_window(title='Programas y características', control_type='Hyperlink').invoke()
app.wait_cpu_usage_lower(threshold=0.5, timeout=30, usage_interval=1.0)

app.window(title='Programas y características').child_window(title='Ver actualizaciones instaladas', control_type='Hyperlink').invoke()
app.wait_cpu_usage_lower(threshold=0.5, timeout=30, usage_interval=1.0)

list_box = app.InstalledUpdates.FolderViewListBox

items = list_box.descendants(control_type='Nombre')
all_updates = [item.window_text() for item in items]
print('\nAll updates ({}):\n'.format(len(all_updates)))
print(all_updates)

windows_items = list_box.child_window(title_re='^Microsoft Windows.*', control_type='Group').descendants(control_type='Nombre')
windows_updates = [item.window_text() for item in windows_items]
print('\nWindows updates only ({}):\n'.format(len(windows_updates)))
print(windows_updates)