import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;
import sayTheSpire.ui.elements.ButtonElement;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

@SpirePatch(clz = MainMenuPanelButton.class, method = "update")
public class MainMenuPanelButtonPatch {

    public static void Postfix(MainMenuPanelButton __instance) {
        if (__instance.hb.justHovered) {
            String header = (String) ReflectionHacks.getPrivate(__instance, MainMenuPanelButton.class, "header");
            String description = (String) ReflectionHacks.getPrivate(__instance, MainMenuPanelButton.class,
                    "description");
            ButtonElement element = new ButtonElement(TextParser.parse(header), TextParser.parse(description));
            Output.setUI(element);
        }
    }
}
