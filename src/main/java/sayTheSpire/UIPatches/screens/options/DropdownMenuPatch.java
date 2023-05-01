import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.DropdownElement;
import sayTheSpire.ui.UIRegistry;

public class DropdownMenuPatch {

    @SpirePatch(clz = DropdownMenu.class, method = "update")
    public static class updatePatch {

        public static void Postfix(DropdownMenu __instance) {
            DropdownElement currentDropdown = (DropdownElement) UIRegistry.getUI(__instance);
            if (currentDropdown == null)
                return;
            if (currentDropdown.getAutoAnnounceHover() && __instance.getHitbox().justHovered) {
                Output.setUI(currentDropdown);
            }
            currentDropdown.update();
        }
    }
}
