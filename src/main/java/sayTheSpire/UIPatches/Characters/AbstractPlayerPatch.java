package sayTheSpire.UIPatches.Characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;

@SpirePatch(clz = AbstractPlayer.class, method = "update")
public class AbstractPlayerPatch {

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
