package sayTheSpire.UIPatches.Characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import sayTheSpire.events.TextEvent;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.Output;

public class AbstractPlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "gainGold", paramtypez = { int.class })
    public static class GainGoldPatch {

        public static void Prefix(AbstractPlayer __instance, int gold) {
            Output.event(new TextEvent("+ " + gold + " gold"));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "obtainPotion", paramtypez = { AbstractPotion.class })
    public static class ObtainPotionPatch {

        public static void Postfix(AbstractPlayer __instance, AbstractPotion potionToObtain) {
            // Avoid triggering the message if the player can't actually pick up the potion
            if (!potionToObtain.isObtained)
                return;
            Output.event(new TextEvent(potionToObtain.name + " obtained"));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "update")
    public static class UpdatePatch {

        // A patch here is necessary because of how convoluted the various card interactions are
        public static AbstractCard prevHoveredCard = null;

        public static void Postfix(AbstractPlayer __instance) {
            if (__instance.hb.justHovered) {
                Output.text(__instance.title, true);
                Output.buffers.setCurrentBuffer("player");
            }
            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
                return;
            if (__instance.hoveredCard != prevHoveredCard) {
                if (__instance.hoveredCard != null) {
                    Output.setUI(new CardElement(__instance.hoveredCard, CardElement.CardLocation.HAND));
                }
                prevHoveredCard = __instance.hoveredCard;
            }
        }
    }
}
