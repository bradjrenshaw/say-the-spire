package sayTheSpire.events;

import sayTheSpire.Output;

public class CombatCardTextEvent extends TextEvent {

    public CombatCardTextEvent(String message) {
        super(message);
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("combat.card_events", true);
    }
}
