import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.EventTextEvent;

public class CardCrawlGamePatch {

  @SpirePatch(clz = CardCrawlGame.class, method = "create")
  public static class CardCrawlGameCreatePatch {

    public static void Postfix() {
      Output.setup();
      if (Output.config.getBoolean("input.virtual_input", false) && CInputHelper.controller == null) {
      CInputHelper.model = CInputHelper.ControllerModel.XBOX_ONE;
            ImageMaster.loadControllerImages(CInputHelper.ControllerModel.XBOX_ONE);
      }
    }
  }

  @SpirePatch(clz = CardCrawlGame.class, method = "update")
  public static class UpdatePatchPostfix {

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
  