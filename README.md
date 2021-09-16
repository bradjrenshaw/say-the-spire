# Say the Spire

A mod for the game Slay the Spire to provide blind accessibility.

* This only works on Windows for now
* Only the Steam version of the game is supported.

## Mod Installation and Usage

For recent changes, see the [change log](changes.md).

For compilation instructions for developers, go [here](devs.md).

### Setup Instructions for Steam

If you own the game on Steam, Say the Spire can be subscribed to (installed) from the Steam Workshop. If you are doing this, it will require you to subscribe to ModTheSpire and BaseMod. Check the bottom of the page after clicking subscribe and it will link you to the required mods. 

If you do not want to install from the steam workshop, copy sayTheSpire.jar to your Slay the Spire mods directory. This is probably C:\Program Files (x86)\Steam\Steamapps\Common\Slay the Spire\mods but may be different depending on where steam is installed. You can either install the dependencies (ModTheSpire and BaseMod) from the steam workshop, or download them from their respective GitHub pages listed below. If you do not have a mods folder in your slayTheSpire directory, create one and everything should work.

* [ModTheSpire](https://github.com/kiooeht/ModTheSpire)
* [BaseMod](https://github.com/daviscook477/BaseMod)

Make sure that you do not have multiple instances of the same mod installed from different sources for Say the Spire or any mods it depends on. for example, do not have both the steam workshop and mods folder versions of BaseMod installed. Conflicting mods can cause the game to break and prevent mods from functioning properly.

After you have installed the needed mods, you can launch the game using one of the following options. See the Mod the Spire section for how to handle some accessibility issues with the modding tool.

* Recommended: Download and run the [mts_steam.cmd](scripts/mts_steam.cmd) file. It shouldn't matter where this file is launched from. If your steam directory is not the default, open the mts_steam.cmd file in notepad or wordpad and change the path after the /d to the correct directory. Whenever you want to play the game using mods, launch that file.
* in steam, right click on the game, click play, OCR, left click on play with mods, then left click on play. This is not ideal but it works.
* Launch using a manual call to java (not recommended)

### Mod the Spire

Mod the Spire is the software that handles game mods. It allows you to activate and deactivate mods. When play is pressed, the mods are loaded into the game and the game is run.

Mod the Spire is not the most accessible piece of software, however it can be worked with. If you are using NVDA you can use object nav to enable/disable particular mods (see below).

If your NVDA doesn't recognize objects in the window, run the following command using cmd prompt or batch. If your steam directory is not default, change the steam path accordingly.

```
"C:\Program Files (x86)\Steam\steamapps\common\SlayTheSpire\jre\bin\jabswitch" -enable
```

This command enables Java Access Bridge. If you run this command on the command prompt, it should output "Java Access Bridge has been enabled".

Note that if you are using jaws, Mod the Spire will not work with certain older versions of jaws due to them not supporting Java Access Bridge (no controls will be read). If you are using jaws, you can hit enter as soon as mod the spire opens to play. Note that doing this will play with the currently enabled mods, however by default no mods are enabled. If setting up mods though you will have to switch to NVDA or some other scree nreader that has Java Access Bridge support.

If you want to quickly enable/disable all mods, do the following:

* If this is the first time running Mod the Spire, click toggle all mods on/off, then click play.
* If this is a subsequent run of Mod the Spire, you want to enable all mods (maybe after adding a new one), and you have already enabled mods, click the toggle all mods on/off button twice then click play. If you haven't changed your mods since the previous run you can just hit play; your mod state is saved.

If you are using NVDA, you can use object Nav to view the information for each mod and to enable/disable the mod. To do so, arrow over the list item for a mod and press the move to first contained object key. You can then use move to previous and next object to interact with this section. This collection of objects includes the mod checkbox (toggle with Activate current Navigator object) as well as the mod's name and other information.

## Usage

### How it works

The mod automatically sets up dll files needed for screen reader functionality and removes them after the game is closed. If the mod is working correctly, after the splash screen closes and the main menu is fully loaded, you will hear your screen reader announce the UI Element you are on (most likely the play/continue button).

You can interact with the game using normal game controls. However, there is an extra set of and keyboard controller inputs to provide information. This is generally refered to as the buffer system and is mapped to the right stick or control plus the arrow keys on keyboard.

As you move over various UI Elements, contextual buffers will appear. These are just lists of various pieces of information about the element you're focusing on. For example, if you hover over a card, the card buffer will be automatically focused and will contain information such as the card's name, energy cost, description, etc.

Think of buffers as a way to refer to different areas of the screen quickly, similarly to how you could scan visually. For example, while hovered over a card, you can move to the player buffer to view player info without having to move there with the other controls. Buffers are also used to provide access to UI Tooltips (for example character information on the character selection screen, etc).

### UI Querks

This game has some unusual qualities to the UI (due to the fact that controller support was added in later; the game was primarily mouse support before that). The default controls are listed here for reference:

| Action | Controller (xbox/ps) | Keyboard |
| ----------- | ----------- | ----------- |
| Movement (menus, general UI) | d-pad or left stick | arrow keys |
| Confirm | a/x | enter |
| Top panel | x/square | t |
| Proceed | y/triangle | e |
| Cancel | b/circle | escape |
| Master deck view/scroll certain things left | lb/l1 | d |
| Draw pile view/some other contextual options | lt/l2 | q |
| Exhaust pile/scroll some things right | rb/r1 | f |
| Discard pile/some contextual options | rt/r2 | w |
| Buffers/some contextual options | right stick | control arrows |
| settings/pause | start | backspace |
| Map/general back option | select | m |

In general keep in mind:

* Certain prompts (confirmation, tutorial, hand card selection, etc) require you to hit proceed to get out of them. For confirmation prompts, you can also hit cancel to back out. Note that this is not always the case.
    * Note that if you have the disable confirm for single card selection option on, you do not have to hit proceed after selecting a card.
* Nearly everything has contextual actions mapped to the various controls. For example, hitting top panel/x/square will allow you to delete a save slot and proceed/y/triangle will allow you to rename that slot.
* For the options screen, for some reason you have to move the right stick left and right to adjust the volume sliders (or ctrl left/right by default on keyboard).
* In many circumstances a grid or hand card select screen will appear. Note that this is not the normal turn play card from hand screen. For example an instance of this is playing warcry (where you have to select a card to put on top of your draw pile). You need to hit proceed to exit this screen after selecting cards. For the hand card select screens, they are laid out in two rows (top row is cards selected, bottom row is cards in hand).

### Config

After first launching the game, two config files are created that allow you to modify certain aspects of the mod. This file can be found at:

* Windows: %LOCALAPPDATA%\ModTheSpire\sayTheSpire\
* Linux: ~/.config/ModTheSpire/sayTheSpire/
* Mac: ~/Library/Preferences/ModTheSpire/sayTheSpire/

The settings.ini file allows you to change various settings related to the mod. The input.json stores keyboard mappings.

### Virtual Input

The virtual input setting allows you to play the game using a keyboard and will provide some future functionality (for both keyboard and controllers) yet to be implemented. Virtual input defaults to enabled, so keyboard support should work as soon as you launch the game for the first time. If you are upgrading from a previous version of Say the Spire, or if you want to disable virtual input, you can modify settings.ini. Set the virtual_input setting to true or false if you want it enabled or disabled respectively. Note that keyboard input will not work with virtual input disabled (it will use the game's existing limited keyboard support).

Keyboard remappings can currently be modified by editing input.json. A better method for remapping is being worked on.

### The Map

While viewing the map, the buffer system is replaced with a map viewer. This allows you to quickly follow paths.

When playing Slay the Spire, you can view the whole map for a given act at any time by pressing map, as well as it being automatically shown between rooms. The map consists of rooms with curved lines indicating where you can travel to. The location of the player is either below the map (beginning of an act) or the room they last completed. 

The map viewer is essentially a virtual cursor. If you move over a map node normally in the course of gameplay (that is, using the d-pad, arrow keys, or left stick), the virtual cursor will be set to that map node.

Use right stick left and right or control left and right arrows to move between possible choices (rooms you can move to from this node, only in the forward direction). Use right stick up or control up arrow to follow the path forward; this will move continuously until you hit a choice or dead end (such as a boss). Use right stick down or control down arrow to retrace your steps.

For example if given the following 3x4 map:

monster, elite, unknown, monster

blank, merchant, unknown, blank

monster, blank, blank, blank

Say you are starting below the  bottom row. You have a single choice (monster, far bottom left) The only choice from there is merchant. There are however three choices (monster, elite, or unknown).
From the bottom room your map focus will be on the first choice (monster). hitting forward will follow the path until it hits a branch point, so it will read something like this.
monster, merchant, choice
After that it will allow you to browse the choices as before. Note that it reads the choice again at the start of the path to avoid confusion, this isn't a bug. If moving backwards, the path is read in reverse as you would expect, unless you disable this setting in the config file.


## Known Issues

* daily climb (leaderboard section doesn't read), input settings, and credits screens do not read. These are being worked on.
* Certain text (such as the character select text screen's title) doesn't automatically read. This is being worked on.
* Required controller inputs are not read/viewable. This is being worked on.
* The UI can jump between cards a few times (for example when retaining cards as the silent). this isn't an issue I can fix and does not break the game in any way.
* Currently certain text is only read in English (for example UI element types).
* Not all UI positions read as you would expect (for example potions and relics don't have position information). This is being worked on