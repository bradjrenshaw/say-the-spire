package sayTheSpire.ui.effects.handlers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import sayTheSpire.Output;
import sayTheSpire.events.ObtainEvent;
import sayTheSpire.ui.effects.EffectHandler;
import sayTheSpire.ui.effects.EffectManager;

public class ObtainKeyEffectHandler extends EffectHandler {

    private String color;

    public ObtainKeyEffectHandler(AbstractGameEffect effect) {
        super(effect);
        ObtainKeyEffect.KeyColor color = (ObtainKeyEffect.KeyColor) ReflectionHacks.getPrivate(this.effect,
                ObtainKeyEffect.class, "keyColor");
        switch (color) {
        case RED:
            this.color = "ruby";
            break;
        case BLUE:
            this.color = "sapphire";
            break;
        case GREEN:
            this.color = "emerald";
            break;
        }
    }

    public void onAdd() {
        Output.event(new ObtainEvent(this.color + " key obtained"));
    }

    public static void registerHandlers(EffectManager manager) {
        manager.registerEffectHandler(ObtainKeyEffect.class, ObtainKeyEffectHandler.class);
    }
}
