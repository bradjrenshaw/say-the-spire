import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.SaveSlot;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

public class SaveSlotPatch {

  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SaveSlot");

  public static final String[] TEXT = uiStrings.TEXT;

  @SpirePatch(clz = SaveSlot.class, method = "update")
  public static class UupdatePatch {

    public static void Postfix(SaveSlot __instance) {
      if (__instance.slotHb.justHovered) {
        int index = (int) ReflectionHacks.getPrivate(__instance, SaveSlot.class, "index");
        String name = (String) ReflectionHacks.getPrivate(__instance, SaveSlot.class, "name");
        long playtime = (long) ReflectionHacks.getPrivate(__instance, SaveSlot.class, "playtime");
        float completionPercentage =
            (float) ReflectionHacks.getPrivate(__instance, SaveSlot.class, "completionPercentage");
        if (!__instance.emptySlot) {
          Output.text(name, true);
          Output.setupUIBufferMany(
              name,
              TextParser.parse(CharStat.formatHMSM(playtime) + TEXT[0]),
              String.format("%.1f", completionPercentage) + '%');
        } else {
          Output.text(TEXT[1], true);
          Output.setupUIBufferMany(TEXT[1]);
        }
      }
    }
  }
}
