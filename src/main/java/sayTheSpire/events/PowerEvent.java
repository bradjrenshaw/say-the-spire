package sayTheSpire.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.Output;
import basemod.ReflectionHacks;

public class PowerEvent extends Event {

    private AbstractCreature source;
    private AbstractCreature target;
    private String text;

    public PowerEvent(AbstractCreature source, AbstractCreature target, AbstractGameEffect effect) {
        this.source = source;
        this.target = target;
        if (effect instanceof PowerBuffEffect) {
            this.text = (String)ReflectionHacks.getPrivate(effect, PowerBuffEffect.class, "msg");
        } else if (effect instanceof PowerDebuffEffect) {
            this.text = (String)ReflectionHacks.getPrivate(effect, PowerDebuffEffect.class, "msg");
        } else {
            this.text = null;
        }
    }

    public String getText() {
        return OutputUtils.getCreatureName(this.target) + " " + this.text;
    }

    public Boolean shouldAbandon() {
        return this.text == null || !Output.config.getBoolean("combat.buff_debuff_text");
    }
}
