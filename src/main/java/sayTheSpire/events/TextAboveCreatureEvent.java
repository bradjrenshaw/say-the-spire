package sayTheSpire.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import sayTheSpire.utils.OutputUtils;

public class TextAboveCreatureEvent extends Event {

    protected AbstractCreature creature;
    protected String text;

    public TextAboveCreatureEvent(AbstractCreature creature, String text) {
        super("textAboveCreature");
        this.creature = creature;
        this.text = text;
        this.context.put("target", OutputUtils.getCreatureName(creature));
        this.context.put("message", this.text);
    }

    public Boolean isComplete() {
        return this.creature != null && this.text != null;
    }

    public Boolean shouldRead() {
        return this.isComplete();
    }
}
