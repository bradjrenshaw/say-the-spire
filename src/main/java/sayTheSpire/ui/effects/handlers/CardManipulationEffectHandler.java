package sayTheSpire.ui.effects.handlers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import basemod.ReflectionHacks;
import sayTheSpire.ui.effects.EffectHandler;
import sayTheSpire.ui.effects.EffectManager;
import sayTheSpire.events.TextEvent;
import sayTheSpire.Output;

public class CardManipulationEffectHandler extends EffectHandler {

    private enum ManipulationType {
        EXHAUST, PURGE, SHOW_ADD_DISCARD, SHOW_ADD_DRAW, SHOW_ADD_HAND, SHOW_CARD_OBTAIN, SHOW_CARD_BRIEFLY,
        FAST_OBTAIN, INVALID
    };

    private ManipulationType manipulationType;
    private AbstractCard card;

    public CardManipulationEffectHandler(AbstractGameEffect effect) {
        super(effect);
        this.setup();
    }

    private void setup() {
        if (this.effect instanceof ExhaustCardEffect) {
            this.manipulationType = ManipulationType.EXHAUST;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ExhaustCardEffect.class, "c");
        } else if (this.effect instanceof PurgeCardEffect) {
            this.manipulationType = ManipulationType.PURGE;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, PurgeCardEffect.class, "card");
        } else if (this.effect instanceof ShowCardAndAddToDiscardEffect) {
            this.manipulationType = ManipulationType.SHOW_ADD_DISCARD;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndAddToDiscardEffect.class,
                    "card");
        } else if (this.effect instanceof ShowCardAndAddToDrawPileEffect) {
            this.manipulationType = ManipulationType.SHOW_ADD_DRAW;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndAddToDrawPileEffect.class,
                    "card");
        } else if (this.effect instanceof ShowCardAndAddToHandEffect) {
            this.manipulationType = ManipulationType.SHOW_ADD_HAND;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndAddToHandEffect.class,
                    "card");
        } else if (this.effect instanceof ShowCardAndObtainEffect) {
            this.manipulationType = ManipulationType.SHOW_CARD_OBTAIN;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardAndObtainEffect.class, "card");
        } else if (this.effect instanceof ShowCardBrieflyEffect) {
            this.manipulationType = ManipulationType.SHOW_CARD_BRIEFLY;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, ShowCardBrieflyEffect.class, "card");
        } else if (this.effect instanceof FastCardObtainEffect) {
            this.manipulationType = ManipulationType.FAST_OBTAIN;
            this.card = (AbstractCard) ReflectionHacks.getPrivate(this.effect, FastCardObtainEffect.class, "card");
        } else {
            this.manipulationType = ManipulationType.INVALID;
            this.card = null;
        }
    }

    public String getMessage() {
        switch (this.manipulationType) {
        case EXHAUST:
            return this.card.name + " exhausted";
        case PURGE:
            return this.card.name + " removed from your deck";
        case SHOW_ADD_DISCARD:
            return this.card.name + " added to discard";
        case SHOW_ADD_DRAW:
            return this.card.name + " added to draw";
        case SHOW_ADD_HAND:
            return this.card.name + " added to hand";
        case SHOW_CARD_BRIEFLY:
            return this.card.name + " shown briefly";
        case SHOW_CARD_OBTAIN:
        case FAST_OBTAIN:
            return this.card.name + " added to deck";
        default:
            return "invalid card manipulation effect";
        }
    }

    public void onAdd() {
        Output.event(new TextEvent(this.getMessage()));
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
