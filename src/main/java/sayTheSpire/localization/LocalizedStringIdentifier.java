package sayTheSpire.localization;

/**
 * A helper class for certain objects that might require a base string to match a specific id but to also allow for
 * tracking the localized text for that identifier (IE parts of the settings interface.)
 */
public class LocalizedStringIdentifier {

    public final String key, localized;

    public LocalizedStringIdentifier(String key, String localized) {
        this.key = key;
        this.localized = localized;
    }

    public String getKey() {
        return this.key;
    }

    public String getLocalized() {
        return this.localized;
    }

}
