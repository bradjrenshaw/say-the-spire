package sayTheSpire.events;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import sayTheSpire.Output;
import sayTheSpire.utils.OutputUtils;

public class PowerEvent extends Event {

    private AbstractCreature source;
    private AbstractCreature target;
    private String text;

    public PowerEvent(AbstractCreature source, AbstractCreature target, AbstractGameEffect effect) {
        super("power");
        this.source = source;
        this.target = target;
        if (effect instanceof PowerBuffEffect) {
            this.text = (String) ReflectionHacks.getPrivate(effect, PowerBuffEffect.class, "msg");
        } else if (effect instanceof PowerDebuffEffect) {
            this.text = (String) ReflectionHacks.getPrivate(effect, PowerDebuffEffect.class, "msg");
        } else {
            this.text = null;
        }
        this.context.put("message", this.text);
        this.context.put("target", OutputUtils.getCreatureName(target));
    }

    public Boolean shouldAbandon() {
        return this.text == null || !Output.config.getBoolean("combat.buff_debuff_text");
    }
}
