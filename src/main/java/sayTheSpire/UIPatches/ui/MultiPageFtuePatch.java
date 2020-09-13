import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.MultiPageFtue;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class MultiPageFtuePatch {

  @SpirePatch(clz = MultiPageFtue.class, method = SpirePatch.CONSTRUCTOR)
  public static class ConstructorPatch {

    public static void Postfix(MultiPageFtue __instance) {
      Output.text(TextParser.parse(MultiPageFtue.MSG[0]), true);
    }
  }

  @SpirePatch(clz = MultiPageFtue.class, method = "update")
  public static class UpdatePatch {

    public static void Prefix(MultiPageFtue __instance) {
      if ((AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft)
          || CInputActionSet.proceed.isJustPressed()) {
        int slot = (int) ReflectionHacks.getPrivate(__instance, MultiPageFtue.class, "currentSlot");
        if (slot == -2) return;
        String text = TextParser.parse(MultiPageFtue.MSG[Math.abs(slot - 1)]);
        Output.text(text, true);
        Output.setupUIBufferMany(text);
      }
    }
  }
}
