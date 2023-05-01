import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import basemod.ReflectionHacks;
import sayTheSpire.ui.elements.DropdownElement;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.Output;

public class RunHistoryScreenPatches {

    private static Object prevHovered = null;

    public static DropdownMenu getDropdown(RunHistoryScreen screen, String name) {
        return (DropdownMenu) ReflectionHacks.getPrivate(screen, RunHistoryScreen.class, name);
    }

    public static Boolean dropdownHovered(DropdownMenu dropdown) {
        if (dropdown.rows.isEmpty())
            return false;
        Hitbox hb = dropdown.getHitbox();
        if (hb == null)
            return false;
        return hb.hovered;
    }

    public static Object getHovered(RunHistoryScreen screen) {
        DropdownMenu runsDropdown = getDropdown(screen, "runsDropdown");
        if (dropdownHovered(runsDropdown))
            return runsDropdown;
        DropdownMenu characterFilter = getDropdown(screen, "characterFilter");
        if (dropdownHovered(characterFilter))
            return characterFilter;
        DropdownMenu winLossFilter = getDropdown(screen, "winLossFilter");
        if (dropdownHovered(winLossFilter))
            return winLossFilter;
        DropdownMenu runTypeFilter = getDropdown(screen, "runTypeFilter");
        if (dropdownHovered(runTypeFilter))
            return runTypeFilter;
        return null;
    }

    @SpirePatch(clz = RunHistoryScreen.class, method = "changedSelectionTo")
    public static class ChangedSelectionToPatch {

        public static void Postfix(RunHistoryScreen __instance) {
            DropdownMenu runsDropdown = getDropdown(__instance, "runsDropdown");
            UIRegistry.register(runsDropdown, new DropdownElement(runsDropdown, "runs"));
        }
    }

    @SpirePatch(clz = RunHistoryScreen.class, method = "refreshData")
    public static class RefreshDataPatch {

        public static void Postfix(RunHistoryScreen __instance) {
            DropdownMenu runsDropdown = getDropdown(__instance, "runsDropdown");
            UIRegistry.register(runsDropdown, new DropdownElement(runsDropdown, "runs", false));
            DropdownMenu characterFilter = getDropdown(__instance, "characterFilter");
            UIRegistry.register(characterFilter, new DropdownElement(characterFilter, "character filter", false));
            DropdownMenu winLossFilter = getDropdown(__instance, "winLossFilter");
            UIRegistry.register(winLossFilter, new DropdownElement(winLossFilter, "win loss filter", false));
            DropdownMenu runTypeFilter = getDropdown(__instance, "runTypeFilter");
            UIRegistry.register(runTypeFilter, new DropdownElement(runTypeFilter, "run type", false));
        }
    }

    @SpirePatch(clz = RunHistoryScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(RunHistoryScreen __instance) {
            Object hovered = getHovered(__instance);
            if (hovered != null && hovered != prevHovered) {
                UIElement element = UIRegistry.getUI(hovered);
                if (element != null)
                    Output.setUI(element);
            }
            prevHovered = hovered;
        }
    }
}
