import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.utils.CardUtils;
import sayTheSpire.utils.OutputUtils;

public class DiscardPileViewScreenPatch {

    @SpirePatch(clz = DiscardPileViewScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(DiscardPileViewScreen __instance) {
            AbstractPlayer player = OutputUtils.getPlayer();
            if (player != null) {
                ArrayList<AbstractCard> cards = player.discardPile.group;
                if (cards == null)
                    return;
                Output.textLocalized("ui.screens.DiscardPileViewScreen.title", false, "cards", cards.size());
            }
        }
    }

    @SpirePatch(clz = DiscardPileViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(DiscardPileViewScreen __instance) {
            AbstractCard controllerCard = (AbstractCard) ReflectionHacks.getPrivate(__instance,
                    DiscardPileViewScreen.class, "controllerCard");
            if (controllerCard == null)
                return;
            if (controllerCard.hb.justHovered) {
                AbstractPlayer player = OutputUtils.getPlayer();
                GridPosition position = null;
                if (player.discardPile != null) {
                    position = CardUtils.getGridPosition(controllerCard, player.discardPile.group, 5);
                }
                Output.setUI(new CardElement(controllerCard, CardElement.CardLocation.DISCARD_PILE_VIEW, position));
            }
        }
    }
}
