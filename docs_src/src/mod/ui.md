# Game UI

## General Overview of UI Querks
This game has some unusual qualities to the UI (due to the fact that controller
support was added in later; the game was primarily mouse driven before that).
for more detail on any of these, see the sections below this list. In general
keep in mind:

* Certain prompts (confirmation, tutorial, hand card selection, etc) require you
  to hit proceed to get out of them. For confirmation prompts, you can also hit
  cancel to back out. Note that this is not always the case.
    * Note that if you have the disable confirm for single card selection option
      on, you do not have to hit proceed after selecting a card.
* Nearly everything has contextual actions mapped to the various controls. For
  example, hitting top panel/x/square will allow you to delete a save slot and
  proceed/y/triangle will allow you to rename that slot.
* For the options screen, for some reason you have to move the right stick left
  and right to adjust the volume sliders (or ctrl left/right by default on
  keyboard).
* In many circumstances a grid or hand card select screen will appear. Note that
  this is not the normal turn play card from hand screen. For example an
  instance of this is playing warcry (where you have to select a card to put on
  top of your draw pile). You need to hit proceed to exit this screen after
  selecting cards. For the hand card select screens, they are laid out in two
  rows (top row is cards selected, bottom row is cards in hand).

## Confirmation Prompts
Prompts such as the abandon run prompt provide a yes/no option. To confirm one
of these prompts, use the proceed input. to cancel one, use the cancel input.

## Hand Card Select
Hand card select prompts consist of two rows. The bottom row contains the cards
you can select from; the top row contains cards that have been selected. You can
hit confirm on a card in the top row to deselect it. Once done, you can hit
proceed (y/triangle/e) to confirm your selections.

## Menus
The menus overall work as expected, however:

* In the main menu, you can get to your save slots by hitting the map input (back/select/m).
    * In the save slots menu, hitting top panel (x/square/t) will allow you to
      delete a save slot and hitting proceed (y/triangle/e) will allow you to
      rename one.
* Character Select
    * hitting proceed (y/triangle/e) will toggle ascension mode, master deck and
      exhaust view inputs (the bumpers or d and f for keyboard) will change the
      ascension level.
* Custom Run
    * Top panel (x/square/t) will toggle ascension mode, deck view and exhaust
      view (the bumpers or d and f for keyboard) will change ascension level
    * Proceed (y/triangle/e) to start the run
* Settings Screen
    * This menu is laid out in a very inconsistent grid of two columns. Moving
      between items is not always consistent, especially when switching columns.
      This is not an issue with the mod but appears to be related to how the
      controller support for the game is implemented.
    * You can change tabs with the deck and exhaust inputs (bumpers or d or f on
      keyboard). Be very careful in the controls tab however as it is not
      supported by the mod yet.
    * Buffer controls next/previous (right stick left/right or ctrl left/right
      arrow) change volume sliders
* In-run pause menu/settings screen
    * To abandon a run, hit proceed (y/triangle/e)
    * to return to the main menu without abandoning the run, hit discard
          (rt/r2/w)
* Singing Bowl Relic requires exhaust view input (rb/r1/f)

## Game UI
The game, both in battle and outside of it, is laid out in a grid.

### Top Panel
The top panel takes up the top part of the screen and holds a number of your
resources, such as your relics. You can get to it at any time by pressing top
panel (x/square/t). You will have to do this to review other buffers if the map
is currently showing. The top panel is always visible, regardless of whether the
map, battle, or an event is currently displayed.

* The top row holds your player hp, your gold, your potions, and the ascension
  level (if applicable).
* The second row shows all of your relics

### Battle
The battle screen is laid out as follows, from bottom to top:

* Your hand
* the second row shows the player and monsters
* the third row shows any orbs and orb slots (if applicable; if not this row is
  not there)
* the top panel

When you play a card that does not require a target, you must hit the confirm
button again in order to play it. If a target is required however you can just
select it without having to double the input.