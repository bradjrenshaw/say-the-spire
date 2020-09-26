# Say the Spire Usage

## Setup

### Installation Instructions

* Say the Spire can be subscribed to (installed) from the Steam Workshop.
* Alternatively, copy sayTheSpire.jar to your Slay the Spire mods directory. If using steam this is probably C:\Program Files (x86)\Steam\Steamapps\Common\Slay the Spire\mods
* Make sure that you do not have multiple instances of the same mod installed from different sources for Say the Spire or any mods it depends on. for example, do not have both the steam workshop and mods folder versions of basemod as things can break in unexpected ways.

### Running the Mod

To play the game using the mod, you need to first launch mod the spire and then enable the mod there. To do so, follow one of the following steps:

* Run the mts.cmd file included in the zip file for the current release (it should not matter where this is run from, so long as the zip is extracted). This is the recommended option if not using the steam workshop version.
* in steam, right click on the game, click play, OCR, left click on play with mods, then left click on play. This is not ideal but it works.
* Launch using a manual call to java (not recommended)

Mod the Spire is not the most accessible piece of software, however it can be worked with. If you are using NVDA you can use object nav to enable/disable particular mods.

Note that if you are using jaws, Mod the Spire will not work with jaws due to jaws having issues with Java Access Bridge (no controls will be read). If you are using jaws, you can hit enter as soon as mod the spire opens to play. If setting up mods though you will have to switch to NVDA or some other scree nreader that has Java Access Bridge support.

If you want to quickly enable/disable all mods, do the following:

* If this is the first time running Mod the Spire, click toggle all mods on/off, then click play.
* If this is a subsequent run of Mod the Spire, you want to enable all mods (maybe after adding a new one), and you have already enabled mods, click the toggle all mods on/off button twice then click play. If you haven't changed your mods since the previous run you can just hit play; your mod state is saved.

## Usage

### How it works

The mod automatically sets up dll files needed for screen reader functionality and removes them after the game is closed. If the mod is working correctly, after the splash screen closes and the main menu is fully loaded, you will hear your screen reader announce the UI Element you are on (most likely the play/continue button).

You can interact with the game using normal game controls. However, there is an extra set of controller inputs to provide information. This is generally refered to as the buffer system and is mapped to the right stick.

As you move over various UI Elements, contextual buffers will appear. These are just lists of various pieces of information about the element you're focusing on. For example, if you hover over a card, the card buffer will be automatically focused and will contain information such as the card's name, energy cost, description, etc.

Move the right stick left and right to switch between buffers; use up and down to review their contents. Think of buffers as a way to refer to different areas of the screen quickly, similarly to how you could scan visually. For example, while hovered over a card, you can move the right stick to the player buffer to view player info without having to move there with the other controls. Buffers are also used to provide access to UI Tooltips (for example character information on the character selection screen, etc).

### UI Querks

This game has some unusual qualities to the UI (due to the fact that controller support was added in later; the game was primarily mouse support before that). The default controls are listed here for reference:

* Movement (menus, general UI): d-pad or left stick
* Confirm: a/x
* Top panel: x/square
* Proceed: y/triangle
* Cancel: b/circle
* Master deck view/scroll certain things left: lb/l1
* Draw pile view/some other contextual options: lt/l2
* Exhaust pile/scroll some things right: rb/r1
* Discard pile/some contextual options: rt/r2
* Buffers/some contextual options: right stick
* Settings (pause): start
* Map/general back option: select

In general keep in mind:

* Certain prompts (confirmation, tutorial, hand card selection, etc) require you to hit proceed to get out of them. For confirmation prompts, you can also hit cancel to back out
    * Note that if you have the disable confirm for single card selection option on, you do not have to hit proceed after selecting a card.
* Nearly everything has contextual actions mapped to the various controls. For example, hitting top panel/x/square will allow you to delete a save slot and proceed/y/triangle will allow you to rename that slot.
* For the options screen, for some reason you have to move the right stick left and right to adjust the volume sliders.
* In many circumstances a grid or hand card select screen will appear. Note that this is not the normal turn play card from hand screen. For example an instance of this is playing warcry (where you have to select a card to put on top of your draw pile). You need to hit proceed to exit this screen after selecting cards. For the hand card select screens, they are laid out in two rows (top row is cards selected, bottom row is cards in hand).

### Config

After first launching the game, a config file is created that allows you to modify certain aspects of the mod. This file can be found at:

* Windows: %LOCALAPPDATA%\ModTheSpire\
* Linux: ~/.config/ModTheSpire/
* Mac: ~/Library/Preferences/ModTheSpire/

The config file is divided into a number of sections. Change the values after each equals sign to change the settings

### The Map

While viewing the map, the buffer system is replaced with a map viewer. This allows you to quickly follow paths.

When playing Slay the Spire, you can view the whole map for a given act at any time by pressing map, as well as it being automatically shown between rooms. The map consists of rooms with curved lines indicating where you can travel to. The location of the player is either below the map (beginning of an act) or the room they last completed. 

The map viewer is essentially a virtual cursor. If you move over a map node normally in the course of gameplay (that is, using the d-pad or left stick), the virtual cursor will be set to that map node.


Use right stick left and right to move between possible choices (rooms you can move to from this node, only in the forward direction). Use right stick up to follow the path forward; this will move continuously until you hit a choice or dead end (such as a boss). Use right stick down to retrace your steps.

For example if given the following 3x4 map:

monster, elite, unknown, monster
blank, merchant, unknown, blank
monster, blank, blank, blank

Say you are starting below the  bottom row. You have a single choice (monster, far bottom left) The only choice from there is merchant. There are however three choices (monster, elite, or unknown).
From the bottom room your map focus will be on the first choice (monster). hitting forward will follow the path until it hits a branch point, so it will read something like this.
monster, merchant, choice
After that it will allow you to browse the choices as before. Note that it reads the choice again at the start of the path to avoid confusion, this isn't a bug. If moving backwards, the path is read in reverse as you would expect, unless you disable this setting in the config file.
