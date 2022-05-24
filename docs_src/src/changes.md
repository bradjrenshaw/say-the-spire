# Change Log

## 0.3.4-beta (2022-05-23)
This is a quick fix to prevent a number of issues prior to adding localization
support.

* The mod will no longer cause the game to crash due to conflicting versions of
  com.google.gson. This was causing crashes with some mods that use gson such as
  Downfall.
* com.google.gson is now used behind the scenes for json loading and saving.
  This should not change mod functionality in any way but this was needed to
  prepare for localization support.
* The mod no longer bundles a number of redundant dependencies in the .jar file.
  This should noticeably reduce the startup time of the game.
* Fixed an error in change log (you remove cards, not sell them) and updated
  documentation important notes with Steam accessibility changes and some
  clarifications.
* input.json now is actually loaded and no longer throws a null pointer
  exception.
* Added some keyboard shortcuts:
    * ctrl+y: Read player energy
    * ctrl+i: Read incoming damage (summarized monster intents)
    * alt+i: Read the intents of all monsters in sequence
* Speech output:
    * Speech handlers can be reordered (for example you could set clipboard
      output first if you prefer)
    * System speech (such as SAPI) can now be forced for speech handlers that
      support it.
## 0.3.3-beta
This should be the last general bug fix and gameplay notification patch before
more features are added.

* Added: Achievement items on the character stats screen now have location
  information.
* Fixed: Block gains are now correctly read out if block was already greater
  than 0 (it was previously triggering on the wrong event and only reading when
  your block was 0 before the gain.)
* Added: Combat orb events are now read out (such as when they are channeled and
  when they are evoked). This can be configured; see the config section of the
  documentation for more info.
* Added: Monster deaths are now read out.
* Fixed: The input.json loading process is no longer horribly broken. This will
  allow for more actions to be mapped successfully without causing the game to
  crash.
* Added additional keyboard hotkeys to quickly read important player
  information:
    * The current act boss (ctrl n)
    * Player Block (ctrl b)
    * Player Gold (ctrl g)
    * Player HP (ctrl h)
* Fixed: The manual installation instructions in the documentation were
  incorrect; this has been fixed.

## 0.3.2-beta

* Fixed: The Gremlin Match game card bug is now actually fixed and will no
  longer read all of the possible cards before the game starts
* Added: The character stats screen is now supported. This screen appears to be
  buggy however and will not let you scroll to the individual character stats
  (below the achievement grid) correctly some of the time. This seems to be a
  game issue and not a mod one.
* A large number of events are now correctly read out:
    * Card manipulation events, such as cards being exhausted and added to your
      hand, draw, discard, and deck as well as the discard pile being shuffled
      into the draw pile
    * Cards are now read out when shown briefly (for example when two random
      cards are upgraded)
    * Obtained gold
    * Obtained key
    * Obtained potion
    * Obtained  relic
* Added: You can now disable obtained events (obtain potion, relic, etc) and in
  combat card events (card exhausted, etc). For information on this, see the
  config documentation.

## 0.3.1-beta
A number of gameplay fixes and additions.

* Added: Blights are now accessible.
* Fixed: The buffers now correctly update when switching between items as per
  the pre-0.2.0 behavior. You should no longer get inaccurate player hp values
  when moving between items in the player buffer for example.
* Added: You can now prevent all element types (such as button) from being read
  or prevent specific element types from being read. See the configuration
  documentation for more information.
* Fixed: Buttons in the main menu are now actually considered buttons by the mod
  so UI settings related to them now work properly.

## 0.3.0-beta
This patch fixes a number of issues related to UI.

* The compendium screens are now fully supported
* The various deck view screens (draw, discard, etc) now read your total number
  of cards on that screen and announce the screen you are on.
* Cards
    * Location now properly announced
    * A new config setting has been added to improve mod compatibility as the
      localization fixes in the last beta broke certain card descriptions that
      were updated during gameplay by various mods. If you want to use certain
      mods that do this, set advanced.use_updated_card_description in your
      settings.ini file to true. Note that this will most likely break
      localization for certain languages (currently confirmed to break for
      Japanese).
    * Fixed an issue where numbers followed by punctuation would read
      incorrectly, for example the Feed card. See the text parsing section.
* Combat Rewards Screen
    * All rewards now have location information and properly update buffers
* Events
    * Fixed potential bug with Gremlin Match game where it would read the names
      of all cards as the event started
* General Gameplay Screen
    * All of the tooltips and associated resources are now viewable by browsing
      the main screen as intended (they are to the left of your potions)
    * The ascension level tooltip is now accessible (to the right of your
      potions)
* Monsters
    * Power tips (the descriptions of any applied powers in the buffer) will no
      longer incorrectly show formatting information if that information occurs
      at the start of the first word of the effect description (for example #y
      at the beginning of weak's description)
    * Monsters now have location information
* Potions
    * Potion location is now properly announced
    * Fixed a bug where you could not read the tooltip for an empty potion slot
    * Potion rarity has been added to the potion buffer
* Relics    
    * Location is now properly announced
    * Relic rarity has been added to the relic buffer
* Shops
    * All shop items now have location information approximately corresponding
      to their on screen position
    * The button to remove a card is now actually announced as a button and has
      location information
* Text Parsing
    * Fixed a bug where words containing numbers would break if the number was
      immediately followed by punctuation. this primarily effected cards such as
      feed, where the 3 hp increase was read as !m!.

## Beta 0.2.0

* The versioning scheme has been changed to semantic versioning. This should
  allow for more clear version numbering for frequent updates instead of large
  spread out beta releases as it has been so far.
* Added: Introduction of the virtual input system allowing for keyboard support
  and additional mod specific menus. See the Virtual Input section of the readme
  for more information.
* Fixed: Strategic (attack and debuff intent) damage now correctly shows
  multihitting attacks
* Fixed: If no screen reader is running, the mod now correctly defaults to using
  SAPI on Windows.
* Fixed: Potential issues with speech not stopping on button press or stopping
  at the wrong time
* Fixed: Resolution options in settings no longer have "tab tab" after them
* Fixed: You can no longer see a face down or locked card's energy cost.
* Fixed: A number of issues related to buffers have been fixed
    * Buffers should no longer eroniously report null items
    * When the mod focuses a buffer (for example when moving over a card and the
      buffer for that card is selected), the buffer now correctly updates. this
      should prevent needing to refocus the buffer to read card information in
      some situations.
    * Fixed a potential crash if no buffer was selected and you tried to read
      the next or previous item.
    * Fixed a long standing bug where trying to review buffer items on the
      treasure chest screen would crash the game.
* Fixed: A number of issues related to parsing text in certain languages
    * Variable numbers on cards should now be read for all languages such as
      damage or block (thanks to @yncat for the code contribution).
    * Fixed issue in settings menu where the language dropdown would have
      formatting tags read for some languages
    * Strings of single characters will no longer crash the game in certain
      situations. This may fix issues with certain non-English language strings
      crashing the game.
* Added: Two new settings have been added to the config file:
  resources.dispose_resource_files and resources.unload_native_libs. These were
  added to deal with a rare issue with certain Steam setups that can cause the
  game to crash upon closing. These are both on by default but should be set to
  false if people are experiencing game crashes. The issue has most likely been
  fixed but these settings are left here just in case.
* Added: Messages sent to logs by Say the Spire have been standardized to fit
  the style of Slay the Spire's logs. This should make debug output a lot more
  clear.
* Added: The Say the Spire version is announced after the splash screen fully
  fades out.
* Fixed: The game should no longer crash on shutdown for some steam setups due
  to unloading the wrong native libraries too early. If a crash on shutdown
  occurs please report it (see the known issues section of this change log).
* Fixed: Dropdown menus that are not yet recognized by the mod will no longer
  cause the game to crash. As a result the run history screen will no longer
  crash the game. Note that the screen itself is still not yet read by the mod.
* Added: Card rarity is now included in the card buffer (thanks to @ohylli for
  the code contribution).

### Known Issues

* There was a rare crash for certain Steam setups that occured when the game is
  closed. It is believed that this issue is fixed, however if this happens, open
  settings.ini and change dispose_resource_files and unload_native_libs to false
  under the resources section. Screen reader dlls and a few .lib files will be
  left in your Slay the Spire program directory but nothing else will be
  effected.


## Beta 4/Public Beta 1

* Fixed: An update to Slay the Spire broke the mod due to a game file being
  removed; this has been fixed. Please report any other issues that may have
  been introduced in the most recent Slay the Spire update.

* Added: Certain UI elements (such as cards and orbs) now read their relative
  positions
* Fixed: The death text quote now reads correctly if you lose multiple runs in
  the same game session
* Added: the proceed option is now read out
* Fixed: You can no longer view card information if the cards are visually face
  down or locked
* Added: Match and Keep (the gremlin card matching event) is now supported
* Added: A config file can now be found in either your local app data (windows)
  or home config directory (linux). Currently the only supported config options
  are UI position reading, banner text reading, proceed reading, in battle block
  gains, in battle buff/debuff text, and reversed path reading.
* Fixed: Removed some redundant checks for hovered cards, hopefully this should
  remove some of the UI issues like some cards being read multiple times (this
  does not effect the hand, which I cannot fix)
* Added: All controller inputs now interrupt speech (as per normal screen reader
  behavior when using a keyboard application). This should make the game feel
  less sluggish.
* Changed: There have been some behind the scenes UI changes. Please report any
  buttons labeled incorrectly.
* Fixed: Hopefully the last of the settings name issues has been fixed (upload
  data no longer has #B in the name)
* Fixed: Dropdown menus in settings now read the elements correctly when the
  dropdown scrolls vertically
* Added: When a new event occurs, the event buffer's position is changed to that
  event
* Fixed: The player name is now correct for when text above them is read during
  combat (this would break when changing save slots).
* Fixed: Sort button headers for card grids should now be reading properly
* Added: Initial support for the daily climb screen. Only the challenge info is
  read currently, the leaderboard will come in the next beta.
* Fixed: Power text in combat should now be reading properly again. This is
  currently experimental and may still be broken. There are some oddities in the
  way text is displayed; I'm outputting how the game displays the text (for
  example when a buf is initially applied, it only says the name and not the
  initial amount).
* Added: In combat block gains are announced.

## Beta 3

* Added: Initial support for sort header buttons, however I don't think this is
  working properly
* Fixed: It was possible to have incorrect speech libraries be loaded or have
  working speech libraries fail to load
* Fixed: Some tooltips in settings were not being parsed correctly
* Fixed: Certain Settings screen items had incorrect tooltips associated with
  them due to the UI buffer not updating
* Added: The custom mode screen is now supported
* Added: Death and victory screens are now supported
* Aded: You can view game over stats on the death and victory screens
  individually (move over them, check buffers for additional descriptions if
  any)
* Added: Hopefully boss chests are read properly.
* Added: The banner is now read (some text at the top of the screen used
  contextually in diferent parts of the game). This includes a short line of
  text on the death screen, screen titles for areas such as awards "spoils!"
  etc.
* Added: When the map does a long scroll animation, the act boss is read

## Beta 2

* Fixed: Player name in player buffer is now correct when switching between save
  slots
* Treasure chests are now read? Hopefully?
* Fixed: Previously missing event text is now read properly (speech interrupt on
  card selection screens, etc was interrupting it)
* Changed: The monster short (text read when hovering over a monster) is now
  more convenient; the intent and block are read before the hp
* Added: keywords on cards are now in the card buffer.
* Fixed: Buffers would crash on certain screens, such as the death screen
* Fixed: Buffers should no longer read "name: null" when moving to them
* Fixed: Main menu buttons would sometimes repeat their label infinitely if you
  skipped past them too quickly (for example mashing select from the game over
  screen)
* Fixed: In combat events should no longer break in weird ways (such as wrong
  enemy names, wrong dialogue associated with each event, etc)
* Added: You can now see the relic and card previews for event choices (they
  show up in their respective buffers)
* Fixed issue where buffers updated at the wrong time (this could lead to
  buffers reading as empty when they were not, etc). also added more accurate
  buffer error messages.

### Changes That Could be Considered Spoilers

* The location of the emerald key is now announced on the map (as burning icon)