import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.utils.CardUtils;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.Output;

public class DrawPileViewScreenPatch {

    public static ArrayList<AbstractCard> getDrawPile(DrawPileViewScreen screen) {
        CardGroup group = (CardGroup) ReflectionHacks.getPrivate(screen, DrawPileViewScreen.class, "drawPileCopy");
        if (group == null)
            return null;
        return group.group;
    }

    @SpirePatch(clz = DrawPileViewScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(DrawPileViewScreen __instance) {
            ArrayList<AbstractCard> cards = getDrawPile(__instance);
            if (cards != null) {
                Output.text("Draw pile view, " + cards.size() + " cards", false);
            }
        }
    }

    @SpirePatch(clz = DrawPileViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(DrawPileViewScreen __instance) {
            AbstractCard controllerCard = (AbstractCard) ReflectionHacks.getPrivate(__instance,
                    DrawPileViewScreen.class, "controllerCard");
            if (controllerCard == null)
                return;
            if (controllerCard.hb.justHovered) {
                ArrayList<AbstractCard> cards = getDrawPile(__instance);
                GridPosition position = null;
                if (cards != null) {
                    position = CardUtils.getGridPosition(controllerCard, cards,
                            (int) ReflectionHacks.getPrivateStatic(DrawPileViewScreen.class, "CARDS_PER_LINE"));
                }
                Output.setUI(new CardElement(controllerCard, CardElement.CardLocation.DRAW_PILE_VIEW, position));
            }
        }
    }
}
