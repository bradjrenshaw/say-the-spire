import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

@SpirePatch(clz = CharacterOption.class, method = "update")
public class CharacterOptionPatch {

  private static final UIStrings uiStrings =
      CardCrawlGame.languagePack.getUIString("CharacterOption");

  public static final String[] TEXT = uiStrings.TEXT;

  public static void Prefix(CharacterOption __instance) {
    if (__instance.hb.justHovered) {
      if (__instance.locked == false) {
        Output.text(__instance.c.title + " button", true);
        String hp = (String) ReflectionHacks.getPrivate(__instance, CharacterOption.class, "hp");
        int gold = (int) ReflectionHacks.getPrivate(__instance, CharacterOption.class, "gold");
        String flavorText =
            (String) ReflectionHacks.getPrivate(__instance, CharacterOption.class, "flavorText");
        int unlocksRemaining =
            (int) ReflectionHacks.getPrivate(__instance, CharacterOption.class, "unlocksRemaining");
        int maxAscensionLevel =
            (int)
                ReflectionHacks.getPrivate(__instance, CharacterOption.class, "maxAscensionLevel");
        Output.setupUIBufferMany(
            __instance.c.title,
            hp + " hp; " + gold + " gold",
            TextParser.parse(flavorText),
            unlocksRemaining + " unlocks remaining",
            maxAscensionLevel + " max ascension level");
      } else {
        Output.text(TEXT[0] + " button", true);
        String unlockText = "";
        switch (__instance.c.chosenClass) {
          case THE_SILENT:
            unlockText = TEXT[1];
            break;
          case DEFECT:
            unlockText = TEXT[3];
            break;
          case WATCHER:
            unlockText = TEXT[10];
            break;
          default:
            unlockText = "Unknown unlock text, report to mod developer.";
        }
        Output.setupUIBufferMany(TEXT[0], unlockText);
      }
    }
  }
}
