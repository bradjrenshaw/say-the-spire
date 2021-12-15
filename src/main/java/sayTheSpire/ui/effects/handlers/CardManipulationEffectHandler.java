package sayTheSpire.ui.effects.handlers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import basemod.ReflectionHacks;
import sayTheSpire.ui.effects.EffectHandler;
import sayTheSpire.ui.effects.EffectManager;
import sayTheSpire.events.CombatCardTextEvent;
import sayTheSpire.events.Event;
import sayTheSpire.events.ObtainTextEvent;
import sayTheSpire.events.TextEvent;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.Output;

public class CardManipulationEffectHandler extends EffectHandler {

    private AbstractCard card;
    private Event event;

    public CardManipulationEffectHandler(AbstractGameEffect effect) {
        super(effect);
        this.setup();
    }

    private void setup() {
        if (this.effect instanceof ExhaustCardEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ExhaustCardEffect.class, "c");
            this.event = new CombatCardTextEvent(this.card.name + " exhausted");
        } else if (this.effect instanceof PurgeCardEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, PurgeCardEffect.class, "card");
            this.event = new TextEvent(this.card.name + " removed from your deck");
        } else if (this.effect instanceof ShowCardAndAddToDiscardEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndAddToDiscardEffect.class,
                    "card");
            this.event = new CombatCardTextEvent(this.card.name + " added to discard");
        } else if (this.effect instanceof ShowCardAndAddToDrawPileEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndAddToDrawPileEffect.class,
                    "card");
            this.event = new CombatCardTextEvent(this.card.name + " added to draw");
        } else if (this.effect instanceof ShowCardAndAddToHandEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndAddToHandEffect.class,
                    "card");
            this.event = new CombatCardTextEvent(this.card.name + " added to hand");
        } else if (this.effect instanceof ShowCardAndObtainEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndObtainEffect.class, "card");
            this.event = new ObtainTextEvent(this.card.name + " added to deck");
        } else if (this.effect instanceof ShowCardBrieflyEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardBrieflyEffect.class, "card");
            this.event = new TextEvent(this.card.name + " shown briefly");
        } else if (this.effect instanceof FastCardObtainEffect) {
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, FastCardObtainEffect.class, "card");
            this.event = new ObtainTextEvent(this.card.name + " added to deck");
        } else {
            this.card = null;
            this.event = new TextEvent("Invalid card manipulation type; report to mod dev.");
        }
    }

    public void onAdd() {
        Output.event(this.event);
    }

    public static void registerHandlers(EffectManager manager) {
        manager.registerEffectHandler(ExhaustCardEffect.class, CardManipulationEffectHandler.class);
        manager.registerEffectHandler(PurgeCardEffect.class, CardManipulationEffectHandler.class);
        manager.registerEffectHandler(ShowCardAndAddToDiscardEffect.class, CardManipulationEffectHandler.class);
        manager.registerEffectHandler(ShowCardAndAddToDrawPileEffect.class, CardManipulationEffectHandler.class);
        manager.registerEffectHandler(ShowCardAndAddToHandEffect.class, CardManipulationEffectHandler.class);
        manager.registerEffectHandler(ShowCardAndObtainEffect.class, CardManipulationEffectHandler.class);
        manager.registerEffectHandler(ShowCardBrieflyEffect.class, CardManipulationEffectHandler.class);
        manager.registerEffectHandler(FastCardObtainEffect.class, CardManipulationEffectHandler.class);
    }
}
