package sayTheSpire.events;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import sayTheSpire.Output;

public class RelicFlashEvent extends Event {

    private AbstractRelic relic;

    public RelicFlashEvent(AbstractRelic relic) {
        super("relicFlash");
        this.relic = relic;
        this.context.put("target", relic.name);
    }

    public Boolean shouldAbandon() {
        return !Output.config.getBoolean("ui.read_relic_flashes");
    }

}
