import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.utils.CardUtils;
import sayTheSpire.utils.OutputUtils;

public class MasterDeckViewScreenPatch {

    public static ArrayList<AbstractCard> getSortedDeck(MasterDeckViewScreen screen) {
        ArrayList<AbstractCard> deck = (ArrayList<AbstractCard>) ReflectionHacks.getPrivate(screen,
                MasterDeckViewScreen.class, "tmpSortedDeck");
        if (deck == null) {
            AbstractPlayer player = OutputUtils.getPlayer();
            if (player != null && player.masterDeck != null) {
                deck = player.masterDeck.group;
            }
        }
        return deck;
    }

    @SpirePatch(clz = MasterDeckViewScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(MasterDeckViewScreen __instance) {
            ArrayList<AbstractCard> deck = getSortedDeck(__instance);
            if (deck != null) {
                Output.textLocalized("ui.screens.MasterDeckViewScreen.title", false, "cards", deck.size());
            }
        }
    }

    @SpirePatch(clz = MasterDeckViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(MasterDeckViewScreen __instance) {
            AbstractCard controllerCard = (AbstractCard) ReflectionHacks.getPrivate(__instance,
                    MasterDeckViewScreen.class, "controllerCard");
            if (controllerCard == null)
                return;
            if (controllerCard.hb.justHovered) {
                GridPosition position = null;
                ArrayList<AbstractCard> sortedDeck = getSortedDeck(__instance);
                if (sortedDeck != null) {
                    position = CardUtils.getGridPosition(controllerCard, sortedDeck,
                            (int) ReflectionHacks.getPrivateStatic(MasterDeckViewScreen.class, "CARDS_PER_LINE"));
                }
                Output.setUI(new CardElement(controllerCard, CardElement.CardLocation.MASTER_DECK_VIEW, position));
            }
        }
    }
}
