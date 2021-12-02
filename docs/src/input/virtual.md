# Keyboard Support and Virtual Input

## Keyboard Support
Say the Spire provides full keyboard support for the game. This is enabled by
enabling the virtual_input setting in the config file (should be enabled by
default). Virtual input also allows for additional mod behavior; see the section
below.

Keyboard support works by emulating gamepad controls. For example, pressing e
(proceed) is equivalent to triggering the same action via a gamepad (y/triangle
by default). The game has a number of UI querks; it is recommended that you read
the [UI](../mod/ui.md) section.

Keyboard remappings can currently be modified by editing input.json. A better
method for remapping is being worked on and it is not recommended you do this
unless you are familiar with json. If you are modifying existing mappings, it
should be safe to just modify the values within quotes.

# What Actually is Virtual Input?
Virtual input is a mod setting that adds a layer of hooks between the game's
pre-existing input and the mod. This essentially means that the mod has
additional control over how input is processed and adds the capacity for the mod
to add new inputs. By default this setting is enabled, but can be disabled in
the config file if you prefer to use the game's input handling. Note that doing
this will disable keyboard support and revert to the game's keyboard handling,
which lacks most of the features that the mod has. The buffer system for gamepad
controls will still work when virtual input is disabled.