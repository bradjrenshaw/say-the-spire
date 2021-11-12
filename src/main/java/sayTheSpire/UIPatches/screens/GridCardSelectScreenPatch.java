import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.Output;

public class GridCardSelectScreenPatch {

    private static Boolean initialWait = false;
    private static AbstractCard prevHoveredCard = null;

    @SpirePatch(clz = GridCardSelectScreen.class, method = "openConfirmationGrid", paramtypez = { CardGroup.class,
            String.class })
    public static class OpenConfirmationGridPatch {

        public static void Postfix(GridCardSelectScreen __instance, CardGroup group, String tipMsg) {
            Output.text(tipMsg, false);
            initialWait = false;
            prevHoveredCard = null;
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "open", paramtypez = { CardGroup.class, int.class,
            String.class, boolean.class, boolean.class, boolean.class, boolean.class })
    public static class OpenPatch {

        public static void Postfix(GridCardSelectScreen __instance, CardGroup group, int numCards, String tipMsg,
                boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge) {
            Output.text(tipMsg, false);
            initialWait = true;
            prevHoveredCard = null;
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class UpdatePatch {

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
                CardElement newElement = new CardElement(currentCard, CardElement.CardLocation.GRID_SELECT);
                if (initialWait) {
                    GridPosition position = (GridPosition) newElement.getPosition();
                    if (position.x != 1 || position.y != 1)
                        return; // internally this screen hovers a lot of cards over multiple frames but it will always
                                // end up at position (1, 1) and the other cards aren't visually noticeable. As a
                                // result, the goal is to only read the intended initially focused card.
                }
                initialWait = false;
                Output.setUI(newElement);
            }
            prevHoveredCard = currentCard;
        }
    }
}
