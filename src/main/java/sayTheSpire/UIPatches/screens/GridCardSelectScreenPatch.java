import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.GridPosition;

public class GridCardSelectScreenPatch {

    private static Boolean initialWait = false;
    private static AbstractCard prevHoveredCard = null;
    private static int waitCheck = 0;

    @SpirePatch(clz = GridCardSelectScreen.class, method = "openConfirmationGrid", paramtypez = { CardGroup.class,
            String.class })
    public static class OpenConfirmationGridPatch {

        public static void Postfix(GridCardSelectScreen __instance, CardGroup group, String tipMsg) {
            Output.text(tipMsg, false);
            initialWait = false;
            waitCheck = 0;
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
            waitCheck = 10;
            prevHoveredCard = null;
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class UpdatePatch {

        public static AbstractCard getHoveredCard(GridCardSelectScreen screen) {
            /*
             * for (AbstractCard card : screen.targetGroup.group) { if (card.hb.hovered) return card; } return null;
             */
            AbstractCard current = (AbstractCard) ReflectionHacks.getPrivate(screen, GridCardSelectScreen.class,
                    "controllerCard");
            return current;
        }

        public static void Postfix(GridCardSelectScreen __instance) {
            AbstractCard currentCard = getHoveredCard(__instance);
            if (currentCard == null) {
                prevHoveredCard = null;
                return; // Avoid issues related to initialWait
            }
            if (currentCard != prevHoveredCard) {
                CardElement newElement = new CardElement(currentCard, CardElement.CardLocation.GRID_SELECT);
                if (initialWait) {
                    GridPosition position = (GridPosition) newElement.getPosition();

                    // internally this screen hovers a lot of cards over multiple frames but it will always
                    // end up at position (1, 1) and the other cards aren't visually noticeable. As a
                    // result, the goal is to only read the intended initially focused card.
                    waitCheck--;
                    if (waitCheck > 0 && (position.x != 1 || position.y != 1))
                        return;
                }
                initialWait = false;
                Output.setUI(newElement);
                prevHoveredCard = currentCard;
            }
        }
    }
}
