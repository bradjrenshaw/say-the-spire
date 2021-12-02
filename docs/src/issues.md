# Known Issues

* there is possibly a bug with some Steam setups where closing the game will crash due to native library files not being unloaded. It is believed this is fixed, but if this still occurs change resources.dispose_resource_files and resources.unload_native_libs to false in settings.ini.
* daily climb (leaderboard section doesn't read), input settings, and credits screens do not read. These are being worked on.
* Certain text (such as the character select text screen's title) don't automatically read. This is being worked on.
* Required controller inputs are not read/viewable. This is being worked on.
* The UI can jump between cards a few times (for example when retaining cards as the silent). this isn't an issue I can fix and does not break the game in any way.
* Currently certain text is only read in English (for example UI element types).
