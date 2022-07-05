package sayTheSpire.events;

import sayTheSpire.localization.LocalizationContext;
import sayTheSpire.Output;

/** Events are an abstraction for handling messages sent to the user in a number of instances. */
public class Event {

    private String eventType;
    protected LocalizationContext context;

    public Event(String eventType) {
        this.eventType = eventType;
        this.context = Output.localization.getContext(this.getLocalizationPath());
    }

    protected String getLocalizationPath() {
        return "events." + eventType;
    }

    /** Called every frame */
    public void update() {
    }

    /**
     * Returns the text that should be read when the event is complete (ready to be moved onto the completed queue).
     */
    public String getText() {
        return "";
    }

    /**
     * Returns true if the event is ready to be moved onto the completed queue and output to the user.
     */
    public Boolean isComplete() {
        return true;
    }

    /**
     * Returns true if the event should be removed from pending events and not added to the queue or read. Useful if the
     * conditions for a timed event are not met for example.
     */
    public Boolean shouldAbandon() {
        return false;
    }

    /**
     * Returns true if the event should be output to the user. This is usually equivalent to isComplete.
     */
    public Boolean shouldRead() {
        return this.isComplete();
    }
}
