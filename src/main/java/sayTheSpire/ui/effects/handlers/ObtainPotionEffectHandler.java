package sayTheSpire.ui.effects.handlers;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import basemod.ReflectionHacks;
import sayTheSpire.ui.effects.EffectHandler;
import sayTheSpire.ui.effects.EffectManager;
import sayTheSpire.events.TextEvent;
import sayTheSpire.Output;

public class ObtainPotionEffectHandler extends EffectHandler {

    private AbstractPotion potion;

    public ObtainPotionEffectHandler(AbstractGameEffect effect) {
        super(effect);
        this.potion = (AbstractPotion) ReflectionHacks.getPrivate(this.effect, ObtainPotionEffect.class, "potion");
    }

    public void onAdd() {
        Output.event(new TextEvent(this.potion.name + " obtained"));
    }

    public static void registerHandlers(EffectManager manager) {
        manager.registerEffectHandler(ObtainPotionEffect.class, ObtainPotionEffectHandler.class);
    }
}
