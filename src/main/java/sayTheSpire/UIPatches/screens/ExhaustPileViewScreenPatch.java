import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.utils.CardUtils;

public class ExhaustPileViewScreenPatch {

    public static ArrayList<AbstractCard> getExhaustPile(ExhaustPileViewScreen screen) {
        CardGroup group = (CardGroup) ReflectionHacks.getPrivate(screen, ExhaustPileViewScreen.class,
                "exhaustPileCopy");
        if (group == null)
            return null;
        return group.group;
    }

    @SpirePatch(clz = ExhaustPileViewScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(ExhaustPileViewScreen __instance) {
            ArrayList<AbstractCard> cards = getExhaustPile(__instance);
            if (cards != null) {
                Output.textLocalized("ui.screens.ExhaustPileViewScreen.title", false, "cards", cards.size());
            }
        }
    }

    @SpirePatch(clz = ExhaustPileViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(ExhaustPileViewScreen __instance) {
            AbstractCard controllerCard = (AbstractCard) ReflectionHacks.getPrivate(__instance,
                    ExhaustPileViewScreen.class, "controllerCard");
            if (controllerCard == null)
                return;
            if (controllerCard.hb.justHovered) {
                ArrayList<AbstractCard> cards = getExhaustPile(__instance);
                GridPosition position = null;
                if (cards != null) {
                    position = CardUtils.getGridPosition(controllerCard, cards,
                            (int) ReflectionHacks.getPrivateStatic(ExhaustPileViewScreen.class, "CARDS_PER_LINE"));
                }
                Output.setUI(new CardElement(controllerCard, CardElement.CardLocation.EXHAUST_PILE_VIEW, position));
            }
        }
    }
}
