package sayTheSpire.events;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EventTextEvent extends Event {

    String text;

    public EventTextEvent(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public Boolean isComplete() {
        return !AbstractDungeon.isScreenUp;
    }
}
