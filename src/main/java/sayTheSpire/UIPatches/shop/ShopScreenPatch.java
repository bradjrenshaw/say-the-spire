import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import com.megacrit.cardcrawl.shop.ShopScreen;
import sayTheSpire.ui.CardElement;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;

public class ShopScreenPatch {

  private static final UIStrings uiStrings =
      CardCrawlGame.languagePack.getUIString("MerchantImageTextNotInGame");
  public static final String[] TEXT = uiStrings.TEXT;

  @SpirePatch(
      clz = ShopScreen.class,
      method = "createSpeech",
      paramtypez = {String.class})
  public static class CreateSpeechPatch {

    public static void Prefix(ShopScreen __instance, String msg) {
      Output.event(new DialogueEvent("says", "Merchant", TextParser.parse(msg, "talk")));
    }
  }

  @SpirePatch(clz = ShopScreen.class, method = "update")
  public static class UpdatePatch {

    public static boolean isPurgeHovered = false;

    public static AbstractCard getJustHoveredCard(ShopScreen screen) {
      for (AbstractCard card : screen.coloredCards) {
        if (card.hb.justHovered) return card;
      }
      for (AbstractCard card : screen.colorlessCards) {
        if (card.hb.justHovered) return card;
      }
      return null;
    }

    public static void Postfix(ShopScreen __instance) {
      AbstractCard card = getJustHoveredCard(__instance);
      if (card != null) {
        OnSaleTag tag =
            (OnSaleTag) ReflectionHacks.getPrivate(__instance, ShopScreen.class, "saleTag");
        String priceString = "";
        if (tag != null && tag.card == card) {
          priceString = "price (sale)";
        } else {
          priceString = "price";
        }
        priceString += ": " + card.price;
        CardElement element = new CardElement(card, CardElement.LocationType.SHOP);
        element.setPriceString(priceString);
        Output.setUI(element);
      } else {
        boolean purgeHovered =
            (Boolean) ReflectionHacks.getPrivate(__instance, ShopScreen.class, "purgeHovered");
        if (purgeHovered == true && purgeHovered != isPurgeHovered) {
          Output.text(TEXT[0] + ", price: " + __instance.actualPurgeCost, true);
        }
        isPurgeHovered = purgeHovered;
      }
    }
  }
}
