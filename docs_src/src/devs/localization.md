# Localization
The mod can be localized into any language the game supports. Currently there are no language files besides English implemented. This page provides information on how the system works and how to submit language files. Keep in mind that this feature is still in early beta and that more strings will be added as the mod develops, though the currently existing strings shouldn't change.

## How it Works
Language files are written in the json format. When Say the Spire outputs localized text, it checks 3 locations in order, falling back to the next one if no localization string matching the text to be localized is found in that file:

* your mod config directory/sayTheSpire/languageTemp.json (note this is the folder where settings for Say the Spire are stored on your hard drive, not in the mod .jar file)
* /localization/language/say-the-spire.json (this is bundled in the mod .jar file)
* /localization/eng/say-the-spire.json (this is bundled in the mod .jar file)

An example of a set of localization strings would look like this (from the localization strings for the card buffer):

```json
    "card": {
      "faceDown": "face down card",
      "localizedName": "current card",
      "localizedNameUpgraded": "upgrade preview",
      "noFurtherUpgrade": "This card cannot be upgraded any further.",
      "noObj": "No card available; report to mod developer.",
      "content": {
        "cost": "{cost} energy",
        "typeAndRarity": "{type} type, {rarity} rarity",
        "unknownKeyword": "Unknown keyword {unknownKeyword} found; report to mod developer."
      }
    },
```

The keys are just references that Say the Spire uses for the localization strings themselves; the strings are the values of each object (the text in quotes after the ':'). Any text surrounded by {}, for example "{cost} energy", is a substitution. When the string is localized, it will substitute the {energy} for the amount of energy required to play the card. As an example, for bash this string would render as "2 energy" in English. Note that substitutions themselves are assumed to be localized (for example, if a substitution {rarity} held a card rarity, the text passed to that substitution would be localized already.)

## How to Localize
To localize the mod for a given language:

* If there is no file for a language, start with the english json file and use that as a base. If you are working on a preexisting language file, copy it from the corresponding src/main/resources/localization directory and use it as a base. You can save this in your mod config directory as languageTemp.json; this file will have priority over any other language file. This means that you won't have to recompile the entire mod every time you make a change.
* Change the strings for all values in each object (do not change any keys). Make sure to preserve all substitutions (for example {maxHealth}). Note that the language file will not automatically reload when it is updated; you must currently restart the game for your changes to take effect.
* When you have finished working on your file, there are a few ways you can submit changes:
    * Fork the repository, copy your file to the correct src/main/resources/localization folder and submit a pull request. This is the recommended approach.
    * Open an issue on the github repository and attach your .json file to it.