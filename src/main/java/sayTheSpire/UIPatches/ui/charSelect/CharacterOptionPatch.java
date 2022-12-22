import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import sayTheSpire.ui.elements.CharacterButtonElement;
import sayTheSpire.Output;
import sayTheSpire.TextParser;

@SpirePatch(clz = CharacterOption.class, method = "update")
public class CharacterOptionPatch {

    public static void Prefix(CharacterOption __instance) {
        if (__instance.hb.justHovered) {
            CharacterButtonElement button = new CharacterButtonElement(__instance);
            Output.setUI(button);
        }
    }
}
