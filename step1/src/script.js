const spawn = require("child_process").spawn;

const updates = spawn("powershell.exe", ["C:\\path_to\\check_updates.ps1"]); // TODO: Update the path to the check_updates.ps1 file before running this script

updates.stdout.on("data", (data) => {
  console.log("stdout: " + data.toString());
});

updates.stderr.on("data", (data) => {
  console.log("stderr: " + data.toString());
});

updates.on("exit", (code) => {
  console.log("child process exited with code " + code.toString());
});

updates.stdin.end();
