# Config Files and Changing Mod Settings
After first launching the game, two config files are created that allow you to
modify certain aspects of the mod. These files can be found at:

* Windows: `%LOCALAPPDATA%\ModTheSpire\sayTheSpire\`
* Linux: `~/.config/ModTheSpire/sayTheSpire/`
* Mac: `~/Library/Preferences/ModTheSpire/sayTheSpire/`

The settings.ini file allows you to change various settings related to the mod.
The input.json stores keyboard mappings. It is not recommended to manually
modify input.json unless you are familiar with json.

## Settings.ini
This is a list of all of the available options in settings.ini. To change one of
these, change the value to the right of the `=`. For example you could disable
reading block text by changing `block_text = true` to `block_text = false`.

Certain config options require an array, a list of values. An array always
starts with `[` and ends with `]`. These values must be enclosed in quotes if
they are strings of text. For example to exclude cards and relics, you would use
`exclude_read_typenames = ["card", "relic"]`

### [advanced]
Advanced settings related to the internals of the mod.

| Setting | Accepted Values | Default Value | Description |
|---|---|---|---|
| use_updated_card_description | true, false | false | whether or not to use a different internal property to get card descriptions. This should be enabled if you are trying to use mods that do not have properly rendered card descriptions. This may break depending on your current game language so use this with caution. |

### [combat]
Settings related to combat messages.

| Setting | Accepted Values | Default Value | Description |
|---|---|---|---|
| block_text | true, false | true | Whether or not to read when a creature gains block. |
| buff_debuff text | true, false | true | Whether or not to read when a creature gains a buff or debuff. |
| card_events | true, false | true | Whether or not card events are read. These include cards being added to draw, discard, etc as well as cards being exhausted. |

### [input]
Settings related to virtual input.

| Setting | Accepted Values | Default Value | Description |
|---|---|---|---|
| virtual_input | true or false | true | Whether or not virtual input is enabled. |

### [map]
Settings related to map reading.

| Setting | Accepted Values | Default Value | Description |
|---|---|---|---|
| read_reversed_paths | true, false | true | Whether or not to read the path in reverse when moving backward on the map, as if you were tracing it visually. Many people prefer to disable this to save time but it is enabled by default for clarity. |

### [ui]
Contains settings related to the game's UI and how certain elements are read
out.

| Setting | Accepted Values | Default Value | Description |
|---|---|---|---|
| read_proceed_text | true, false | true | Whether or not to read the proceed text when the option is available (for example to skip a combat reward) |
| read_banner_text | true, false | true | Whether or not banner text is read. Banner text appears at the top of the screen and generally acts as a screen title. |
| read_positions | true, false | true | Whether or not to read the position of an element (for example 1 of 3 in a list) |
| read_types | true, false | true | Whether or not element types, such as button, are read for each UI element. |
| read_obtain_events | true, false | true | Whether or not the mod notifies you when you obtain an object (such as a card, potion, relic, etc). |
| exclude_read_typenames | [array of strings] | [] empty | An array or list of types to not be read. For example if you do not want cards and relics to say card or relic after their name, you could set this to `["card", "relic"]`. |

### [resources]
Settings related to how resources are loaded and unloaded. If you are
experiencing a crash when closing the game, set both of these to false.

| Setting | Accepted Values | Default Value | Description |
|---|---|---|---|
| unload_native_libs | true, false | true | Whether or not to unload the screen reader libraries from memory when the game closes. |
| dispose_resource_files | true, false | true | Whether or not to delete the screen reader dll files in the slay the Spire root directory after the game closes and they are unloaded from memory. Do not enable this unless unload_native_libs is enabled or it won't work. |
