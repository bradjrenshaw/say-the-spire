package sayTheSpire.buffers;

public class EventBuffer extends Buffer {

    public EventBuffer(String name) {
        super("event", name);
    }

    public void add(String toAdd) {
        super.add(toAdd);
        this.movePosition(this.size() - 1);
    }

    public Boolean getEnabled() {
        return true;
    }

    public void setEnabled(Boolean enabled) {
        return;
    }
}
