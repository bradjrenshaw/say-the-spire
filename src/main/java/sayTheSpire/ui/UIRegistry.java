package sayTheSpire.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import sayTheSpire.Output;
import sayTheSpire.localization.LocalizedStringIdentifier;
import sayTheSpire.ui.elements.UIElement;

/**
 * The UIRegistry allows you to associate in game objects with virtual UI elements. Also contains a registry of
 * typenames.
 */
public class UIRegistry {

    private static HashMap<Object, UIElement> entries = new HashMap();
    private static HashSet<LocalizedStringIdentifier> registeredTypenames = new HashSet<>();

    /**
     * Returns the object associated with a UIElement instance (inverts the object->UIElement mapping)
     */
    public static Object getObject(UIElement value) {
        for (Map.Entry<Object, UIElement> entry : entries.entrySet()) {
            Object key = entry.getKey();
            if (entry.getValue() == value)
                return key;
        }
        return null;
    }

    /** Returns the UIElement associated with the given in game object. */
    public static UIElement getUI(Object key) {
        return entries.get(key);
    }

    /**
     * Registers an in game object to map to a specific UIElement.
     *
     * @param key
     *            The in game object
     * @param value
     *            the UIElement corresponding to that in game object
     */
    public static void register(Object key, UIElement value) {
        entries.put(key, value);
    }

    /**
     * Unregisters the UIElement associated to the given in game object.
     *
     * @param key
     *            The in game object
     * 
     * @return true if the key had an association and if it was unregistered, false otherwise
     */
    public static Boolean unregister(Object key) {
        if (entries.containsKey(key)) {
            entries.remove(key);
            return true;
        }
        return false;
    }

    public static void registerTypename(String typename, String localized) {
        registeredTypenames.add(new LocalizedStringIdentifier(typename, localized));
    }

    private static void registerBaseTypenames(String... typenames) {
        // handle localization of element types from Say the Spire itself
        for (String base : typenames) {
            String localized = base;
            String result = Output.localization.getStringAtPath("ui.types." + base);
            if (result != null)
                localized = result;
            registerTypename(base, localized);
        }
    }

    public static void registerDefaultTypenames() {
        registerBaseTypenames("achievement", "blight", "button", "card", "character button", "dropdown", "monster",
                "orb", "potion", "relic", "toggle button", "slider", "character stats", "treasure chest");
    }

    public static Set<String> getRegisteredTypenames() {
        return registeredTypenames.stream().map(t -> t.getKey()).collect(Collectors.toSet());
    }

    public static Set<LocalizedStringIdentifier> getRegisteredTypenameIdentifiers() {
        return registeredTypenames;
    }

    public static void setup() {
        registerDefaultTypenames();
    }

    /** Updates all UIElements in the registry */
    public static void update() {
        for (UIElement value : entries.values()) {
            value.update();
        }
    }
}
