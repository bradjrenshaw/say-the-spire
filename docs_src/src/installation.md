# Mod Installation and Setup

For recent changes, see the [change log](./changes.md).

For compilation instructions for developers, go [here](./devs.md).

## Installation

### Using Steam Workshop (Recommended)
If you own the game on Steam, Say the Spire can be subscribed to (installed) from the Steam Workshop. If you are doing this, it will require you to subscribe to ModTheSpire and BaseMod. Check the bottom of the page after clicking subscribe and it will link you to the required mods. Alternatively all requirements are linked to in the list below.

* [ModTheSpire Steam Workshop](https://steamcommunity.com/workshop/filedetails/?id=1605060445)
* [BaseMod Steam Workshop](https://steamcommunity.com/workshop/filedetails/?id=1605833019)
* [Say the Spire Steam Workshop](https://steamcommunity.com/sharedfiles/filedetails/?id=2239220106)

When you are subscribed to Steam Workshop items they will be automatically downloaded. Updates to those items are also automatic. Say the Spire will announce the current version number when it is launched.

### Manual Installation
If you do not want to install from the steam workshop, you can copy all requirements manually. 

First, download all dependencies. You can either install them from the Steam Workshop links above or download them from their GitHub pages listed below.

* [ModTheSpire GitHub Latest Release](https://github.com/kiooeht/ModTheSpire/releases/latest/) Download and extract ModTheSpire.zip
* [BaseMod GitHub Latest Release](https://github.com/daviscook477/BaseMod/releases/latest/) Download BaseMod.jar
* [Say the Spire GitHub Latest Release](https://github.com/bradjrenshaw/say-the-spire/releases/latest/) download and extract the zip file

Now create a folder called `mods` in your Slay the Spire game directory. This is most likely to be `C:\Program Files (x86)\Steam\steamapps\common\SlayTheSpire`. After that, paste ModTheSpire.jar, BaseMod.jar, and sayTheSpire.jar into that mods folder.

Make sure that you do not have multiple instances of the same mod installed from different sources for Say the Spire or any mods it depends on. for example, do not have both the steam workshop and mods folder versions of BaseMod installed. Conflicting mods can cause the game to break and prevent mods from functioning properly.

Note that you can have different mods from different sources. For example you can have ModTheSpire from the workshop and BaseMod from GitHub.

## Setup
After you have installed the needed mods, you can launch the game using one of the following options. See the Mod the Spire section for how to handle some accessibility issues with the modding tool.

* Recommended: Download and run the [mts_steam.cmd](https://raw.githubusercontent.com/bradjrenshaw/say-the-spire/master/scripts/mts_steam.cmd) file or use the same file from the GitHub release' zip folder. It shouldn't matter where this file is launched from. If your steam directory is not the default, open the mts_steam.cmd file in notepad or wordpad and change the path after the /d to the correct directory. Whenever you want to play the game using mods, launch that file.
    * Your Slay the Spire base directory may already have an `mts.cmd`. If it does you can launch that.
* in steam, right click on the game, click play, OCR, left click on play with mods, then left click on play. This is not ideal but it works.
* Launch using a manual call to java (not recommended)

## Mod the Spire
Mod the Spire is the software that handles game mods. It allows you to activate and deactivate mods. When play is pressed, the mods are loaded into the game and the game is run.

Mod the Spire is not the most accessible piece of software, however it can be worked with. NVDA object navigation is required for more specific mod configuration, however you do not need it for enabling all mods to start with.

If your NVDA doesn't recognize objects in the window (reading "unknown" or just reading nothing at all), run the following command using cmd prompt or batch. If your steam directory is not default, change the steam path accordingly.

```bash
"C:\Program Files (x86)\Steam\steamapps\common\SlayTheSpire\jre\bin\jabswitch" -enable
```

This command enables Java Access Bridge. If you run this command on the command prompt, it should output "Java Access Bridge has been enabled".

Note that if you are using jaws, Mod the Spire will not work with certain older versions of jaws due to them not supporting Java Access Bridge (no controls will be read). If you are using jaws, you can hit enter as soon as mod the spire opens to play. Note that doing this will play with the currently enabled mods, however by default no mods are enabled. If setting up mods though you will have to switch to NVDA or some other scree nreader that has Java Access Bridge support.

### Quick Mod Setup
If you want to quickly enable/disable all mods, do the following. This is most likely what you want if setting up the game for the first time or just running Say the Spire with no additional game mods.

* If this is the first time running Mod the Spire, click toggle all mods on/off, then click play. This will enable all mods as new mods start out disabled by default. 
* If this is a subsequent run of Mod the Spire, you want to enable all mods (maybe after adding a new one), and you have already enabled mods, click the toggle all mods on/off button twice then click play. If you haven't changed your mods since the previous run you can just hit play; your mod state is saved.

### Specific Mod Configuration
If you are using NVDA, you can use object Nav to view the information for each mod and to enable/disable the mod. To do so, arrow over the list item for a mod and press the move to first contained object key. You can then use move to previous and next object to interact with this section. This collection of objects includes the mod checkbox (toggle with Activate current Navigator object) as well as the mod's name and other information.
