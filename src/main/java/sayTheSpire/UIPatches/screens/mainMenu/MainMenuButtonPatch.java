/*
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton.ClickResult;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import basemod.ReflectionHacks;
import sayTheSpire.Output;

@SpirePatch(clz = MenuButton.class, method = "update")
public class MainMenuButtonPatch {

	public static void Prefix(MenuButton __instance) {
		String label = (String)ReflectionHacks.getPrivate(__instance, MenuButton.class, "label");
		if (__instance.hb.justHovered) {
			Output.text(label + " button", true);
		}
	}
}
*/
