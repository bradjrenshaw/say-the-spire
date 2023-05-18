import java.util.ArrayList;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import sayTheSpire.ui.elements.ButtonElement;
import sayTheSpire.ui.positions.CategoryListPosition;
import sayTheSpire.Output;

public class LeaderboardScreenPatches {

    private static Object prevHovered = null;
    private static Object currentHovered = null;
    private static CategoryListPosition currentPosition = null;

    public static void determineHoveredInList(ArrayList<FilterButton> buttons, String filterName) {
        if (currentHovered != null)
            return;
        int count = buttons.size();
        if (count == 0)
            return;
        for (int i = 0; i < count; i++) {
            FilterButton button = buttons.get(i);
            if (button.hb.hovered) {
                currentPosition = new CategoryListPosition(i, count, filterName);
                currentHovered = button;
                return;
            }
        }
    }

    public static void determineHoveredElement(LeaderboardScreen screen) {
        currentHovered = null;
        currentPosition = null;
        determineHoveredInList(screen.charButtons, "character buttons");
        determineHoveredInList(screen.regionButtons, "region buttons");
        determineHoveredInList(screen.typeButtons, "type buttons");
    }

    @SpirePatch(clz = LeaderboardScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(LeaderboardScreen __instance) {
            determineHoveredElement(__instance);
            if (currentHovered != null && currentHovered != prevHovered) {
                FilterButton button = (FilterButton) currentHovered;
                Output.setUI(new ButtonElement(button.label, null, currentPosition));
            }
            prevHovered = currentHovered;
        }
    }
}
