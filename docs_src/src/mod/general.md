# General Mod Usage
The mod automatically sets up dll files needed for screen reader functionality
and removes them after the game is closed. If the mod is working correctly, you
will hear the current version of Say the Spire read out by either your screen
reader or SAPI if your screen reader is not running when the game launches.

It is highly recommended that you refer to the [Keyboard Support/Virtual
Input](../input/virtual.md) and [UI](./ui.md) sections after reading this page.

The mod narrates UI elements automatically. It will also announce many game
events as they occur without any additional player input required. To access
additional statistics and tooltips however you will need to use the buffer
system.

there is an extra set of controller and keyboard inputs to provide information.
This is generally refered to as the buffer system and is mapped to the right
stick or control plus the arrow keys on keyboard by default. As you move over
various UI Elements, contextual buffers will appear. These are just lists of
various pieces of information about the element you're focusing on. For example,
if you hover over a card, the card buffer will be automatically focused and will
contain information such as the card's name, energy cost, description, etc.

Think of buffers as a way to refer to different areas of the screen quickly,
similarly to how you could scan visually. For example, while hovered over a
card, you can move to the player buffer to view player info without having to
move there with the other controls. Buffers are also used to provide access to
UI Tooltips (for example character information on the character selection
screen, etc). 

The buffer system is also used for the map. Using the buffer controls lets you
browse available paths and choices when available. For more information, see the
[map](./map.md) section.