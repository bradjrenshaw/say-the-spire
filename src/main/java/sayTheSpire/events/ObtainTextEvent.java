package sayTheSpire.events;

import sayTheSpire.Output;

public class ObtainTextEvent extends TextEvent {

    public ObtainTextEvent(String message) {
        super(message);
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("ui.read_obtain_events", true);
    }
}
