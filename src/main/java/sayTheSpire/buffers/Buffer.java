package sayTheSpire.buffers;

import java.util.ArrayList;
import java.util.Collection;
import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.Output;

/**
 * A buffer represents a list of data about a given UI Element or in game display. Buffers are often associated with a
 * particular in game object.
 */
public class Buffer {

    protected String type;
    private String name;
    protected int position;
    private Boolean enabled;
    private ArrayList<String> contents;
    protected LocalizationContext context;

    public Buffer() {
        this("unknown", "");
    }

    public Buffer(String type, String name) {
        this.contents = new ArrayList<String>();
        this.type = type;
        this.name = name;
        this.position = 0;
        this.enabled = false;
        if (type == null || type.equals("")) {
            this.context = Output.localization.getContext("");
        } else {
            this.context = Output.localization.getContext("buffers." + type);
        }
    }

    public void add(String toAdd) {
        if (toAdd == null)
            return;
        this.contents.add(toAdd);
    }

    public void addLocalized(String key) {
        this.add(this.context.localize(key));
    }

    public void addMany(Collection<String> collection) {
        for (String text : collection) {
            this.add(text);
        }
    }

    public void clear() {
        this.contents.clear();
    }

    public Boolean isEmpty() {
        return this.contents.isEmpty();
    }

    public Boolean movePosition(int newPosition) {
        this.update();
        if (newPosition < 0 || newPosition >= this.contents.size())
            return false;
        this.position = newPosition;
        return true;
    }

    public Boolean moveToNext() {
        return this.movePosition(this.position + 1);
    }

    public Boolean moveToPrevious() {
        return this.movePosition(this.position - 1);
    }

    /** This method is called by the BufferManager. Do not call this method manually. */
    public void update() {
        return;
    }

    public String getCurrentItem() {
        if (this.position >= this.contents.size())
            this.position = 0;
        if (this.contents.isEmpty())
            return null;
        return this.contents.get(this.position);
    }

    public int size() {
        return this.contents.size();
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalizedName() {
        if (this.type == "unknown")
            return this.getName();
        String name = this.context.localize("localizedName");
        if (name == null)
            return this.getName();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObject() {
        return null;
    }

    public void setObject(Object object) {
        return;
    }

    public int getPosition() {
        return this.position;
    }
}
