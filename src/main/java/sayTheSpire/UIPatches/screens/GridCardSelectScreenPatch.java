import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import sayTheSpire.ui.CardElement;
import sayTheSpire.Output;
import sayTheSpire.utils.CardUtils;

@SpirePatch(clz = GridCardSelectScreen.class, method = "update")
public class GridCardSelectScreenPatch {
  public static AbstractCard hoveredCard = null;

  public static void Postfix(GridCardSelectScreen __instance) {
    AbstractCard currentCard =
        (AbstractCard)
            ReflectionHacks.getPrivate(__instance, GridCardSelectScreen.class, "hoveredCard");
    if (currentCard != null && currentCard != hoveredCard) {
      Output.setUI(new CardElement(currentCard, CardElement.LocationType.GRID_SELECT));
      hoveredCard = currentCard;
    }
  }
}
