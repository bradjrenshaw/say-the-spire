import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.EventTextEvent;

public class CardCrawlGamePatch {

  @SpirePatch(clz = CardCrawlGame.class, method = "create")
  public static class CardCrawlGameCreatePatch {

    public static void Postfix() {
      Output.setup();
    }
  }

  @SpirePatch(clz = CardCrawlGame.class, method = "update")
  public static class UpdatePatch {

    public static void updateGameEvents() {
      if (Output.eventText != null) {
        Output.event(new EventTextEvent(TextParser.parse(Output.eventText)));
        Output.eventText = null;
      }
    }

    public static void Postfix(CardCrawlGame __instance) {
      Output.update();
      updateGameEvents();
    }
  }
}
  