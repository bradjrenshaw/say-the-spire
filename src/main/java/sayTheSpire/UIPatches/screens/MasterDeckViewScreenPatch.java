import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.ui.elements.CardElement;
import sayTheSpire.ui.positions.GridPosition;
import sayTheSpire.utils.OutputUtils;
import sayTheSpire.Output;

public class MasterDeckViewScreenPatch {

    public static ArrayList<AbstractCard> getCards(MasterDeckViewScreen screen) {
        return (ArrayList<AbstractCard>) ReflectionHacks.getPrivate(screen, MasterDeckViewScreen.class,
                "tmpSortedDeck");
    }

    @SpirePatch(clz = MasterDeckViewScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(MasterDeckViewScreen __instance) {
            AbstractPlayer player = OutputUtils.getPlayer();
            if (player != null) {
                ArrayList<AbstractCard> cards = player.masterDeck.group;
                if (cards == null)
                    return;
                Output.text("Master deck view, " + cards.size() + " cards", false);
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
                Output.setUI(new CardElement(controllerCard, CardElement.CardLocation.MASTER_DECK_VIEW));
            }
        }
    }
}
