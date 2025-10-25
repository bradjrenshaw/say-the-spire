package sayTheSpire.events;

public class MiscEvent extends Event {

    private String name;

    public MiscEvent(String name) {
        super("misc");
        this.name = name;
    }

    public String getText() {
        return this.context.localize(this.name);
    }
}
