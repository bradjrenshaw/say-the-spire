package sayTheSpire.ui;

import java.util.ArrayList;
import sayTheSpire.TextParser;

public class Descriptor {

    private String name;

    private String label;

    private String description;

    public Descriptor(String name) {
        this(name, name, null);
    }

    public Descriptor(String name, String label) {
        this(name, label, null);
    }

    public Descriptor(String name, String label, String description) {
        this.name = name;
        this.label = label;
        this.description = description;
    }

    public String getName() {
        return this.name;
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
        else if (this.getName() != null)
            contents.add(TextParser.parse(this.getName()));
        if (this.getDescription() != null)
            contents.add(TextParser.parse(this.getDescription()));
        return contents;
    }
}
