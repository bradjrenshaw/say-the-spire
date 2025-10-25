import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.ui.elements.ButtonElement;

@SpirePatch(clz = MenuButton.class, method = "update")
public class MenuButtonPatch {

    public static void Postfix(MenuButton __instance) {
        if (__instance.hb.justHovered && CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.MAIN_MENU) {
            String label = (String) ReflectionHacks.getPrivate(__instance, MenuButton.class, "label");
            ButtonElement element = new ButtonElement(TextParser.parse(label));
            Output.setUI(element);
        }
    }
}
