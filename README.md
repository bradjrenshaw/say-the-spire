# Say the Spire

A mod for the game Slay the Spire that provides blind accessibility.

* This only works on Windows and Linux for now
* Only the Steam version of the game is officially supported, however modding may work with other versions.

## Installation and Usage
Documentation for the mod can be found [here](https://bradjrenshaw.github.io/say-the-spire).

## Features

* Keyboard support
* The game outputs text to screen readers. NVDA, JAWS, Window-Eyes, Supernova, System Access, and Zoom Text are supported, with SAPI or text being copied to clipboard as a fallback.
* In game UI is read out including map information
* A convenient buffer system (mapped to the right stick and control plus arrow keys by default) to provide easy access to all relevant information for currently visible game elements.
* Narration of in battle events, such as cards drawn, buffs/debuffs applied, etc.
* A configuration file to customize the mod to your liking.

## Known Issues

* there is possibly a bug with some Steam setups where closing the game will crash due to native library files not being unloaded. It is believed this is fixed, but if this still occurs change resources.dispose_resource_files and resources.unload_native_libs to false in settings.ini.
* daily climb (leaderboard section doesn't read), input settings, and credits screens do not read. These are being worked on.
* Certain text (such as the character select text screen's title) don't automatically read. This is being worked on.
* Required controller inputs are not read/viewable. This is being worked on.
* The UI can jump between cards a few times (for example when retaining cards as the silent). this isn't an issue I can fix and does not break the game in any way.
* The resolution dropdown in the settings screen appears buggy with the latest beta. This is actually an issue with the game; the dropdown can't be interacted with when full screen is disabled and with certain screen resolutions. The mod was previously reading selections when it shouldn't have been.

## Beta Testers
Thanks to the beta testers who tested the public and private betas, including but not limited to:

* Ahmad Abukhdair
* Austin Hicks
* Brandon Cole [Website](https://brandoncole.net/)
* Kelsey Hendrix
* Mike Breedlove
* SightlessKombat [Website](https://www.SightlessKombat.com/)
