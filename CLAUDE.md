# Claude Development Guide for Say the Spire

## Project Overview

Say the Spire is a screen reader accessibility mod for Slay the Spire. It provides blind accessibility through:
- Screen reader integration (NVDA, JAWS, Window-Eyes, Supernova, System Access, Zoom Text, SAPI, clipboard fallback)
- Linux support via Speech Dispatcher
- Keyboard and controller support
- A buffer system for browsing game elements
- In-game narration of events
- Dynamic UI system for mod settings and menus
- Downfall mod compatibility (reversed map, reserves, essence, tempHp, boss names)

**Version**: 0.5.1-beta (targeting 1.0)
**Language**: Java 8
**Build Tool**: Maven
**Mod Framework**: ModTheSpire with BaseMod

## Understanding the Mod Architecture

### This is a Runtime Patch Mod

**Critical Concept**: Say the Spire is NOT a standalone application. It is a **mod** that patches the existing Slay the Spire game at runtime.

**How it works:**
1. **Slay the Spire** (`lib/desktop-1.0.jar`) - The base game JAR containing all game code
2. **ModTheSpire** (`lib/ModTheSpire.jar`) - The mod loader framework that enables runtime bytecode patching
3. **BaseMod** (`lib/BaseMod.jar`) - A utility mod providing hooks and helpers for mod development
4. **Downfall** (`lib/Downfall.jar`) - Optional mod for compilation; Say the Spire has compatibility features for Downfall
5. **Say the Spire** (this project) - Patches game classes to add accessibility features

### The Patching System

**SpirePatch** is the core mechanism for modifying game behavior:

```java
@SpirePatch(clz = AbstractCard.class, method = "update")
public class AbstractCardPatch {
    public static void Prefix(AbstractCard __instance) {
        // This code runs BEFORE AbstractCard.update() in the base game
        // __instance is the actual game object
    }

    public static void Postfix(AbstractCard __instance) {
        // This code runs AFTER AbstractCard.update() in the base game
    }
}
```

**Patch Types:**
- `Prefix` - Runs before the original method
- `Postfix` - Runs after the original method
- `Insert` - Inserts code at a specific location (advanced)
- `Replace` - Completely replaces the original method (rare, dangerous)

**What this means for development:**
- You're working with **external game classes** like `AbstractCard`, `AbstractDungeon`, `AbstractMonster`
- These classes come from `desktop-1.0.jar`, not this project
- You cannot modify the game classes directly; you can only patch them
- The `__instance` parameter in patches refers to the actual game object
- You have read access to all game state but limited write access (be careful!)

### External Dependencies on Game Code

When you see imports like:
```java
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
```

These are **game classes**, not mod classes. They exist in `desktop-1.0.jar` and are referenced at compile time but patched at runtime.

**Common game packages:**
- `com.megacrit.cardcrawl.cards.*` - Card system
- `com.megacrit.cardcrawl.characters.*` - Player characters
- `com.megacrit.cardcrawl.monsters.*` - Enemies
- `com.megacrit.cardcrawl.dungeons.*` - Game state, dungeon management
- `com.megacrit.cardcrawl.screens.*` - UI screens
- `com.megacrit.cardcrawl.ui.*` - UI components
- `com.megacrit.cardcrawl.actions.*` - Game actions
- `com.megacrit.cardcrawl.relics.*` - Relics
- `com.megacrit.cardcrawl.potions.*` - Potions
- `com.megacrit.cardcrawl.orbs.*` - Orbs (Defect character)

### Compilation vs Runtime

**At compile time:**
- Maven references `desktop-1.0.jar` to resolve game classes
- Your patches are compiled as normal Java classes
- SpirePatch annotations are preserved

**At runtime:**
- ModTheSpire loads Slay the Spire
- ModTheSpire scans Say the Spire JAR for `@SpirePatch` annotations
- Bytecode is modified to inject your patch code into game methods
- Game runs with your patches active

**This means:**
- You must have `desktop-1.0.jar` in `lib/` to compile
- You cannot debug easily (no direct method calls, everything is patched)
- Testing requires running the actual game with the mod loaded
- Changes require rebuilding the JAR and restarting the game

### Why This Matters

1. **You can't change game behavior directly** - Only through patches
2. **Game object state is external** - Wrap game objects in UIElements to add accessibility info
3. **Be conservative with patches** - Only patch what's necessary; game updates can break patches
4. **Test in game** - No unit tests possible; must test with actual game running
5. **Read game code** - Understanding game classes is essential (though not included in this repo)

## Project Architecture

### Core Components

1. **Output System** (`Output.java`)
   - Central hub for all speech/text output
   - Manages speech handlers, buffers, localization, UI registry, and map manager
   - Use `Output.text(String text, Boolean interrupt)` for raw speech output
   - Use `Output.setUI(UIElement element)` to announce UI element focus

2. **Speech Handlers** (`speech/`)
   - `SpeechManager.java` - Manages multiple speech output methods
   - `TolkHandler.java` - Windows screen reader integration
   - `SpeechdHandler.java` - Linux Speech Dispatcher integration
   - `ClipboardHandler.java` - Fallback clipboard method
   - Priority order is configurable by user

3. **UI System** (`ui/`)
   - `elements/` - UI element representations (cards, monsters, buttons, etc.)
   - `dynamic/` - Say the Spire's own UI system for settings/menus
   - `UIRegistry.java` - Tracks all active UI elements
   - `UIManager.java` - Context-based UI management

4. **Buffer System** (`buffers/`)
   - Provides browsable information containers
   - `BufferManager.java` - Central buffer management
   - Specialized buffers: Card, Monster, Relic, Potion, Orb, Blight, Player, Event, Leaderboard
   - Users navigate buffers with right stick/ctrl+arrow keys

5. **Event System** (`events/`)
   - Announces game events (card drawn, damage taken, etc.)
   - `EventManager.java` - Manages event queue and dispatch
   - Events can be toggled via settings

6. **Localization** (`localization/`)
   - `LocalizationManager.java` - Handles multi-language support
   - JSON-based localization files in `src/main/resources/localization/`
   - Fallback chain: temp → user's language → English
   - Use `Output.localization.localize(String path, Object... args)` for localized strings

7. **Configuration** (`config/`)
   - `SettingsManager.java` - Hierarchical settings system
   - `Setting.java`, `BooleanSetting.java`, `KeyArraySetting.java`
   - Settings stored as JSON
   - Can be modified in-game via dynamic UI

8. **Input System** (`ui/input/`)
   - Virtual input system for keyboard/controller
   - `InputManager.java` - Central input handling
   - `InputAction.java` - Represents bindable actions
   - Supports remapping via dynamic UI

9. **Map System** (`map/`)
   - Virtual map representation for screen reader navigation
   - `VirtualMap.java`, `VirtualMapNode.java`, `VirtualMapEdge.java`
   - Announces node types, paths, player location

10. **Patches** (`UIPatches/`)
    - Uses SpirePatch to hook into game code
    - Organized by game component (cards/, monsters/, screens/, etc.)
    - Typically inject code in update() methods to detect focus changes

### Directory Structure

```
src/main/java/sayTheSpire/
├── Output.java                    # Central output manager
├── SayTheSpire.java              # Public API for other mods
├── ModInitializer.java           # BaseMod initialization
├── TextParser.java               # Cleans up game text for speech
├── STSConfig.java                # Config file I/O
├── BufferControls.java           # Buffer navigation input handling
├── InfoControls.java             # Info shortcut keys (ctrl+h for HP, etc.)
├── MiscTriggers.java             # Miscellaneous game event triggers
├── buffers/                      # Buffer implementations
├── config/                       # Settings system
├── events/                       # Game event system
├── localization/                 # Localization support
├── map/                          # Map representation
├── speech/                       # Speech output handlers
├── ui/
│   ├── elements/                 # UI element wrappers
│   ├── dynamic/                  # Dynamic UI system (mod's own UI)
│   │   ├── contexts/            # UI context management
│   │   ├── elements/            # Dynamic UI elements
│   │   ├── events/              # Dynamic UI events
│   │   └── screens/             # Dynamic UI screens
│   ├── effects/                  # Effect announcement handlers
│   ├── input/                    # Virtual input system
│   ├── mod/                      # UI context and game state tracking
│   ├── navigators/               # Tree/map navigation logic
│   └── positions/                # Position tracking for elements
├── UIPatches/                    # SpirePatch implementations
│   ├── actions/                  # Game action patches
│   ├── cards/                    # Card-related patches
│   ├── Characters/               # Player character patches
│   ├── core/                     # Core game patches
│   ├── events/                   # Event patches
│   ├── helpers/                  # Helper class patches
│   ├── map/                      # Map patches
│   ├── monsters/                 # Monster patches
│   ├── screens/                  # Screen patches
│   ├── shop/                     # Shop patches
│   └── ui/                       # UI component patches
└── utils/                        # Utility classes
```

## Development Guidelines

### 1. Adding Screen Reader Support for New Game Elements

When a new game element needs accessibility:

**Step 1: Create a UIElement class** (if needed)
- Extend `UIElement` in `ui/elements/`
- Implement `getLabel()` - main text to announce
- Implement `getDescription()` - detailed info for buffers
- Implement `getTypeKey()` - element type identifier (e.g., "button", "card")
- Implement `handleBuffers()` if element needs buffer information

**Step 2: Create a SpirePatch**
- Create patch in appropriate `UIPatches/` subdirectory
- Use `@SpirePatch` annotation with target class and method
- Common pattern: Hook `update()` method, check for `hb.justHovered`
- Call `Output.setUI(new YourUIElement(gameObject))` on focus

**Step 3: Register with UIRegistry** (if needed)
- Call `UIRegistry.register(yourElement)` to track updates
- Call `UIRegistry.unregister(yourElement)` on cleanup

**Example Pattern:**
```java
@SpirePatch(clz = SomeGameClass.class, method = "update")
public class SomeGameClassPatch {
    public static void Prefix(SomeGameClass __instance) {
        if (__instance.hb.justHovered) {
            Output.setUI(new YourUIElement(__instance));
        }
    }
}
```

### 2. Adding New Events

When game events need to be announced:

**Step 1: Create an Event class**
- Extend `Event` in `events/`
- Constructor should capture relevant data
- Override `getText()` to return announcement text
- Set appropriate delay if needed

**Step 2: Dispatch the event**
- From patch or other code: `Output.eventManager.addEvent(new YourEvent(...))`
- Events are queued and dispatched with timing control

**Step 3: Add config toggle** (optional)
- Add boolean setting in `SettingsManager.buildDefaults()`
- Check config before dispatching event

### 3. Adding Dynamic UI Elements

For mod's own UI (settings, menus, etc.):

**Step 1: Create a DynamicElement**
- Extend `DynamicElement` or use existing: `DynamicButton`, `DynamicToggleButton`
- Provide label, description, type
- Implement input handlers: `processInputPressed()`, `processInputJustPressed()`, `processInputReleased()`

**Step 2: Add to a Screen or Container**
- Create or extend a `Screen` subclass
- Use `ListContainer` or `ElementContainer` to organize elements
- Handle navigation in `enterFocus()` and `exitFocus()`

**Step 3: Handle events**
- Add event listeners: `element.click.add()`, `element.toggleChange.add()`, etc.
- Dispatch events when appropriate

**Example:**
```java
DynamicButton button = new DynamicButton("My Button", "Button description");
button.click.add((event) -> {
    // Handle button click
});
container.add(button);
```

### 4. Adding Settings

**Step 1: Add to SettingsManager**
- Edit `SettingsManager.buildDefaults()`
- Add setting: `category.addBoolean("setting_name", defaultValue)`
- For key arrays: `category.addKeyArray("setting_name")`
- Chain `.setLocked(true)` if setting shouldn't be changed in-game

**Step 2: Access settings**
- `Output.config.getBoolean("category.setting_name")`
- `Output.config.getString("category.setting_name")`
- `Output.config.getExcludedTypenames()` - Returns set of UI element type names that should not be announced (e.g., user configured to not hear "button" after button labels)

**Step 3: Add localization**
- Add to `src/main/resources/localization/eng/say-the-spire.json`
- Path format: `"settings": { "category": { "setting_name": "Display Name" } }`

### 5. Working with Buffers

**Creating a buffer:**
```java
Output.buffers.clear(); // Clear old buffer content
Output.buffers.add("bufferName", "Item 1");
Output.buffers.add("bufferName", "Item 2");
Output.buffers.focus("bufferName"); // Optional: set focus
```

**Specialized buffer:**
```java
CardBuffer cardBuffer = Output.buffers.getCardBuffer();
cardBuffer.update(cardInstance);
```

### 6. Localization

**Adding localized strings:**
- Add to `src/main/resources/localization/eng/say-the-spire.json`
- Use nested JSON structure for organization
- Variable interpolation supported via LocalizationContext

**Using localized strings:**
```java
// Simple lookup
String text = Output.localization.localize("path.to.string");

// With interpolation
String text = Output.localization.localize("path.to.string",
    "varName", value,
    "otherVar", otherValue);
```

**English-only initially is acceptable** - bulk localization happens later.

### 7. Text Parsing

**Always clean game text:**
- Game text contains formatting codes (#y, #r, !M!, NL, TAB, etc.), color codes (#r, #g, #b, #y, #p), emphasis markers (*word, ~word~, @word@), and energy symbols ([E], [R], etc.)
- Use `TextParser.parse(String text)` or context-specific variants before outputting
- Parser strips formatting codes, converts NL to newlines, handles dynamic variables

**Parsing methods:**
```java
// Basic parsing (removes all formatting)
String clean = TextParser.parse(rawText);

// Context-aware parsing
String cardText = TextParser.parse(rawText, card);  // For AbstractCard
String potionText = TextParser.parse(rawText, potion);  // For AbstractPotion
String relicText = TextParser.parse(rawText, relic);  // For AbstractRelic
String blightText = TextParser.parse(rawText, blight);  // For AbstractBlight

// With custom context and dynamic variables
String parsed = TextParser.parse(text, "card", dynamicVariablesMap);
```

### 8. Input Actions

Input actions are defined in `InputBuilder.java` and processed through the context system.

**Adding new input actions** (in `InputBuilder.buildBaseActionCollection()`):
```java
// Basic action with keyboard mapping (ctrl=true, shift=false, alt=false, key)
actions.addAction("my action").addKeyboardMapping(true, false, false, Keys.X);

// Action with controller mapping
actions.addAction("my action")
    .addKeyboardMapping(false, false, false, Keys.M)
    .addControllerMapping(controllerPrefs.getInteger("MAP", 6));

// Required action (must have at least one mapping)
actions.addAction("my action").addKeyboardMapping(true, false, false, Keys.M).setRequired();
```

**Handling input in a Context** (in `GameContext.java` or custom context):
```java
public Boolean onJustPress(InputAction action) {
    switch (action.getKey()) {
    case "my action":
        // Handle the action
        return true;
    }
    return true;
}
```

**Checking input directly:**
```java
InputAction action = Output.inputManager.getActiveActionCollection().getAction("my action");
if (action != null && action.isJustPressed()) {
    // Handle input
}
```

### 9. Map Tags

**Adding dynamic map information:**
```java
// Via public API
SayTheSpire api = new SayTheSpire();
api.addNodeTag(mapNode, "burning icon"); // Adds tag to node
api.moveNodeTag(sourceNode, "tag", destNode, false); // Moves tag
api.removeNodeTag(mapNode, "tag"); // Removes tag
```

## Code Style and Conventions

### Naming Conventions
- **Classes**: PascalCase (e.g., `CardElement`, `BufferManager`)
- **Methods**: camelCase (e.g., `getLabel()`, `handleBuffers()`)
- **Variables**: camelCase (e.g., `cardElement`, `isHovered`)
- **Constants**: UPPER_SNAKE_CASE (rare in this codebase)
- **Packages**: lowercase (e.g., `sayTheSpire.ui.elements`)

### Method Naming Patterns
- `get*()` - Returns a value, should not have side effects
- `getKey()` - Returns identifier key (never localize). Used for internal identifiers like setting keys, buffer names, etc.
- `getName()` - Returns actual name (may need localization). Only used when the returned value is truly a name (e.g., `MonsterElement.getName()` returns the monster's name)
- `getLabel()` - Returns display text for UI elements (can be localized). This is the primary method for getting human-readable element text.
- `set*()` - Sets a value
- `handle*()` - Processes or manages something
- `on*()` - Event handler/callback
- `process*()` - Processes input or data

**Important distinction (from 0.5.0 changelog):**
In version 0.5.0, `getName()` was deliberately replaced with `getKey()` throughout most of the codebase to make it clear when methods return identifiers vs display names. The pattern is:
- Use `getKey()` when returning an identifier that should never be shown directly to users
- Use `getLabel()` for the main display text of UI elements
- Only use `getName()` when the actual semantic meaning is "this object's name" (like a monster's name)

### Code Organization
- **One class per file** (standard Java)
- **Group related classes in packages** (buffers/, events/, etc.)
- **Patches mirror game structure** (UIPatches follows game's package structure)
- **Keep UI logic separate from game logic**

### Formatting
- **Use Maven formatter plugin** - Run `mvn formatter:format` before commits
- **Default formatting settings** - No custom config required
- Code will be automatically formatted during build

### Comments and Documentation
- **JavaDoc for public API methods** - Especially in `SayTheSpire.java`
- **Inline comments for complex logic** - Explain "why", not "what"
- **Document SpirePatch purpose** - Note which game interaction is being handled
- **Accessibility considerations** - Note why certain text is announced

### Accessibility Best Practices
1. **Always provide text alternatives** - Every visual element needs speech equivalent
2. **Meaningful labels** - Use descriptive names, not just "button 1"
3. **Position information** - Include when helpful for spatial understanding
4. **Consistent terminology** - Use same terms throughout (e.g., always "HP", not mixed "HP/Health")
5. **Configurable verbosity** - Allow users to disable verbose info
6. **Interrupt speech appropriately** - Only interrupt for important info
7. **Timing matters** - Don't spam speech; queue events with appropriate delays
8. **Test without screen** - Imagine using this without visual feedback

## Common Patterns and Idioms

### Pattern 1: UI Element with Buffer
```java
public class MyElement extends UIElement {
    private SomeGameObject gameObject;

    public MyElement(SomeGameObject obj) {
        super("myElementType");
        this.gameObject = obj;
    }

    @Override
    public String getLabel() {
        return gameObject.name;
    }

    @Override
    public String getDescription() {
        return gameObject.description;
    }

    @Override
    public String handleBuffers(BufferManager buffers) {
        buffers.clear();
        buffers.add("ui", this.getLabel(), this.getDescription());
        return "ui"; // Focus UI buffer
    }
}
```

### Pattern 2: Conditional Event Announcement
```java
if (Output.config.getBoolean("combat.card_events")) {
    Output.eventManager.addEvent(new CombatCardTextEvent(card, "drawn"));
}
```

### Pattern 3: SpirePatch with Focus Detection
```java
@SpirePatch(clz = GameClass.class, method = "update")
public class GameClassPatch {
    public static void Prefix(GameClass __instance) {
        // Guard clauses first
        if (AbstractDungeon.screen != null) {
            switch (AbstractDungeon.screen) {
                case SOME_SCREEN:
                    break;
                default:
                    return; // Only handle specific screens
            }
        }

        // Then check hover
        if (__instance.hb.justHovered) {
            Output.setUI(new MyElement(__instance));
        }
    }
}
```

### Pattern 4: Localized String with Variables
```java
String text = Output.localization.localize("combat.damage_taken",
    "amount", damage,
    "source", monster.name);
// With localization file: "combat": { "damage_taken": "Took {amount} damage from {source}" }
```

### Pattern 5: Optional Mod Compatibility with ReflectionHacks
When adding features that depend on other mods (like Downfall), use reflection to avoid hard dependencies:

```java
// Check if a mod is loaded and call its methods without compile-time dependency
int reserves = 0;
try {
    // Load the class dynamically - fails gracefully if mod not present
    Class<?> newReserves = Class.forName("collector.util.NewReserves");
    // Call static method via ReflectionHacks
    reserves = ReflectionHacks.privateStaticMethod(newReserves, "reserveCount").invoke();
} catch (Throwable ignored) {
    // Mod not loaded or method not found - use default value
}

// For accessing fields on objects
try {
    Class<?> cls = Class.forName("com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField");
    Object spireField = cls.getField("tempHp").get(null);
    int tempHp = (int) spireField.getClass().getMethod("get", Object.class).invoke(spireField, player);
} catch (Throwable t) {
    // Feature not available
}
```

**Key principles:**
- Always wrap in try-catch with `Throwable` to handle any reflection errors
- Use `Class.forName()` to check if mod classes exist at runtime
- Use `ReflectionHacks` from BaseMod for calling private/static methods
- Provide sensible defaults when the optional mod isn't present
- This pattern is used for Downfall support (reserves, essence, tempHp, reversed map detection)

## Build and Development Workflow

### Building
```bash
mvn clean package
```
- Compiles code
- Bundles dependencies (Tolk, speechd, interpolatd)
- Outputs to `./mods/sayTheSpire.jar`
- Formats code automatically

### Manual Formatting
```bash
mvn formatter:format
```

### Project Dependencies
- **ModTheSpire** (lib/ModTheSpire.jar) - Mod loading framework
- **BaseMod** (lib/BaseMod.jar) - Mod utilities and hooks
- **Slay the Spire** (lib/desktop-1.0.jar) - Game JAR
- **Downfall** (lib/Downfall.jar) - Optional; enables Downfall-specific accessibility features
- **Tolk** - Windows screen reader library
- **speechd** - Linux speech dispatcher
- **interpolatd** - String interpolation

### Branch Naming
- `features/*` - New features (e.g., `features/dynamicElements`)
- `master` - Main branch for PRs

## Known Issues and Limitations

From README and changes.md:
1. **Steam library cleanup bug** - Rare crash on close; set `resources.dispose_resource_files` and `resources.unload_native_libs` to false if occurs
2. **No automated testing** - Manual testing required (game UI dependent)
3. **Controller mod menu access** - Controller method to access mod menu (ctrl+m) is being worked on
4. **Card hand navigation** - Cards can be read multiple times due to game behavior
5. **Resolution dropdown buggy** - Game issue, not mod issue
6. **Downfall partial support** - Basic Downfall features work (reversed map, reserves, essence, boss names) but not all Downfall content has been tested

## Future Considerations

1. **Plugin System** - `Plugin.java` stub exists for future extension points
2. **Localization bulk updates** - English-only development acceptable initially
3. **Version 1.0 goal** - Focus on completing remaining inaccessible screens
4. **API expansion** - More public API methods as needed

## Key Files Reference

- **Output.java** - Central output hub; most commonly used class
- **SayTheSpire.java** - Public API for other mods
- **ModInitializer.java** - Mod entry point
- **SettingsManager.java** - All settings defined here
- **LocalizationManager.java** - Localization system
- **UIElement.java** - Base class for all UI elements
- **DynamicElement.java** - Base class for mod's own UI
- **BufferManager.java** - Buffer system
- **EventManager.java** - Event queue and dispatch
- **InputBuilder.java** - Defines all input actions and their default mappings
- **GameContext.java** - Handles input actions during normal gameplay
- **MapUtils.java** - Map utilities including Downfall detection

## Quick Reference: Common Tasks

**Add speech output:**
```java
Output.text("Message here", false);
```

**Announce UI element:**
```java
Output.setUI(new MyUIElement(gameObject));
```

**Add event:**
```java
Output.eventManager.addEvent(new MyEvent());
```

**Check setting:**
```java
boolean enabled = Output.config.getBoolean("category.setting");
```

**Localize string:**
```java
String text = Output.localization.localize("path.to.key");
```

**Clean game text:**
```java
String clean = TextParser.parse(rawText);
```

**Add buffer item:**
```java
Output.buffers.add("bufferName", "content");
```

## Tips for Working with Claude

1. **Always read relevant files first** - Don't guess at implementation details
2. **Check recent commits** - Understand current work in progress
3. **Follow established patterns** - Look for similar implementations
4. **Consider accessibility impact** - Think about screen reader user experience
5. **Localization can wait** - English strings acceptable for initial development
6. **Test thoroughly** - No automated tests, so manual testing critical
7. **Reference file:line** - Use format like `Output.java:42` when discussing code
