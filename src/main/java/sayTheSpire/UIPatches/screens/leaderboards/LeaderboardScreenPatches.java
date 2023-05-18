import java.util.ArrayList;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import sayTheSpire.ui.elements.ButtonElement;
import sayTheSpire.ui.elements.LeaderboardFilterButtonElement;
import sayTheSpire.ui.positions.CategoryListPosition;
import sayTheSpire.ui.UIRegistry;
import sayTheSpire.Output;

public class LeaderboardScreenPatches {

    public static void registerButtonList(ArrayList<FilterButton> buttons, String filterName) {
        int count = buttons.size();
        if (count == 0)
            return;
        for (int i = 0; i < count; i++) {
            FilterButton button = buttons.get(i);
            CategoryListPosition position = new CategoryListPosition(i, count, filterName);
            UIRegistry.register(button, new LeaderboardFilterButtonElement(button, position));
        }
    }

    @SpirePatch(clz = LeaderboardScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(LeaderboardScreen __instance) {
            registerButtonList(__instance.charButtons, "character buttons");
            registerButtonList(__instance.regionButtons, "region buttons");
            registerButtonList(__instance.typeButtons, "type buttons");
        }
    }

}
