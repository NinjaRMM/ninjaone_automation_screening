const { exec } = require('child_process');

function getInstalledPatches() {
    return new Promise((resolve, reject) => {
        exec('system_profiler SPInstallHistoryDataType', (err, stdout, stderr) => {
            if (err) {
                reject(err);
                return;
            }

            if (stderr) {
                reject(stderr);
                return;
            }

            const patches = stdout
                .split(/\n\s*\n/)
                .slice(1)
                .map(line => line.trim())
                .reduce((groups, current, index, original) =>
                    index % 2 == 0
                        ? groups.concat([original.slice(index, index + 2)])
                        : groups, [])
                .filter(line => line.length > 1)
                .map(patch => {
                    const version = patch[1].match(/Version: (.*)\n/);
                    const date = patch[1].match(/Install Date: (.*)/)
                    return {
                        name: patch[0].replace(':', ''),
                        version: version ? version[1] : null,
                        date: date ? date[1] : null
                    }
                });

            resolve(patches);
        });
    });
}

function isAutomaticUpdatesEnabled() {
    return new Promise((resolve, reject) => {
        exec('softwareupdate --schedule', (err, stdout, stderr) => {
            if (err) {
                reject(err);
                return;
            }

            if (stderr) {
                reject(new Error(stderr));
                return;
            }

            if (stdout.includes('is turned on')) {
                resolve(true);
            } else {
                resolve(false);
            }
        });
    });
}

getInstalledPatches().then(patches => console.log(patches));
isAutomaticUpdatesEnabled().then(flag => console.log(flag));
