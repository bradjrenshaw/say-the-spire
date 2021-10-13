import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.Output;

public class GridCardSelectScreenPatch {

    @SpirePatch(clz = GridCardSelectScreen.class, method = "open", paramtypez = { CardGroup.class, int.class,
            String.class, boolean.class, boolean.class, boolean.class, boolean.class })
    public static class OpenPatch {

        public static void Postfix(GridCardSelectScreen __instance, CardGroup group, int numCards, String tipMsg,
                boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge) {
            Output.text(tipMsg, false);
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class UpdatePatch {

        public static AbstractCard prevHoveredCard = null;

        public static AbstractCard getHoveredCard(GridCardSelectScreen screen) {
            for (AbstractCard card : screen.targetGroup.group) {
                if (card.hb.hovered)
                    return card;
            }
            return null;
        }

        public static void Postfix(GridCardSelectScreen __instance) {
            AbstractCard currentCard = getHoveredCard(__instance);
            if (currentCard != prevHoveredCard && currentCard != null) {
                Output.setUI(new CardElement(currentCard, CardElement.CardLocation.GRID_SELECT));
            }
            prevHoveredCard = currentCard;
        }
    }
}
