package sayTheSpire.ui.elements;

import java.util.ArrayList;
import sayTheSpire.TextParser;

public class Descriptor {

    private String key;

    private String label;

    private String description;

    public Descriptor(String key) {
        this(key, key, null);
    }

    public Descriptor(String key, String label) {
        this(key, label, null);
    }

    public Descriptor(String key, String label, String description) {
        this.key = key;
        this.label = label;
        this.description = description;
    }

    public String getKey() {
        return this.key;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<String> getUIBufferContents() {
        ArrayList<String> contents = new ArrayList();
        if (this.getLabel() != null)
            contents.add(TextParser.parse(this.getLabel()));
        else if (this.getKey() != null)
            contents.add(TextParser.parse(this.getKey()));
        if (this.getDescription() != null)
            contents.add(TextParser.parse(this.getDescription()));
        return contents;
    }
}
