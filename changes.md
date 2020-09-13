# Change Log

## Beta 4

* Added: Certain UI elements (such as cards and orbs) now read their relative positions
* Fixed: The death text quote now reads correctly if you lose multiple runs in the same game session
* Added: the proceed option is now read out
* Fixed: You can no longer view card information if the cards are visually face down or locked
* Added: Match and Keep (the gremlin card matching event) is now supported
* Added: A config file can now be found in either your local app data (windows) or home config directory (linux). Currently the only supported config options are UI position reading, banner text reading, proceed reading, and reversed path reading.
* Fixed: Removed some redundant checks for hovered cards, hopefully this should remove some of the UI issues like some cards being read multiple times (this does not effect the hand, which I cannot fix)
* Added: All controller inputs now interrupt speech (as per normal screen reader behavior when using a keyboard application). This should make the game feel less sluggish.
* Changed: There have been some behind the scenes UI changes. Please report any buttons labeled incorrectly.
* Fixed: Hopefully the last of the settings name issues has been fixed (upload data no longer has #B in the name)
* Fixed: Dropdown menus in settings now read the elements correctly when the dropdown scrolls vertically
* Added: When a new event occurs, the event buffer's position is changed to that event
* Fixed: The player name is now correct for when text above them is read during combat (this would break when changing save slots).
* Fixed: Sort button headers for card grids should now be reading properly
* Added: Initial support for the daily climb screen. Only the challenge info is read currently, the leaderboard will come in the next beta.

## Beta 3

* Added: Initial support for sort header buttons, however I don't think this is working properly
* Fixed: It was possible to have incorrect speech libraries be loaded or have working speech libraries fail to load
* Fixed: Some tooltips in settings were not being parsed correctly
* Fixed: Certain Settings screen items had incorrect tooltips associated with them due to the UI buffer not updating
* Added: The custom mode screen is now supported
* Added: Death and victory screens are now supported
* Aded: You can view game over stats on the death and victory screens individually (move over them, check buffers for additional descriptions if any)
* Added: Hopefully boss chests are read properly.
* Added: The banner is now read (some text at the top of the screen used contextually in diferent parts of the game). This includes a short line of text on the death screen, screen titles for areas such as awards "spoils!" etc.
* Added: When the map does a long scroll animation, the act boss is read

## Beta 2

* Fixed: Player name in player buffer is now correct when switching between save slots
* Treasure chests are now read? Hopefully?
* Fixed: Previously missing event text is now read properly (speech interrupt on card selection screens, etc was interrupting it)
* Changed: The monster short (text read when hovering over a monster) is now more convenient; the intent and block are read before the hp
* Added: keywords on cards are now in the card buffer.
* Fixed: Buffers would crash on certain screens, such as the death screen
* Fixed: Buffers should no longer read "name: null" when moving to them
* Fixed: Main menu buttons would sometimes repeat their label infinitely if you skipped past them too quickly (for example mashing select from the game over screen)
* Fixed: In combat events should no longer break in weird ways (such as wrong enemy names, wrong dialogue associated with each event, etc)
* Added: You can now see the relic and card previews for event choices (they show up in their respective buffers)
* Fixed issue where buffers updated at the wrong time (this could lead to buffers reading as empty when they were not, etc). also added more accurate buffer error messages.

### Changes That Could be Considered Spoilers

* The location of the emerald key is now announced on the map (as burning icon)