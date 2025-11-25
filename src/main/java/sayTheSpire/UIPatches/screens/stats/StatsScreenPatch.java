import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import java.util.ArrayList;
import sayTheSpire.Output;
import sayTheSpire.ui.elements.AchievementElement;
import sayTheSpire.ui.elements.StatElement;
import sayTheSpire.ui.elements.UIElement;
import sayTheSpire.ui.positions.CategoryListPosition;

public class StatsScreenPatch {

    @SpirePatch(clz = StatsScreen.class, method = "update")
    public static class UpdatePatch {

        private static Hitbox currentHitbox = null;

        private static AchievementElement getHoveredAchievement(StatsScreen screen) {
            int achievementCount = screen.achievements.items.size();
            for (int i = 0; i < achievementCount; i++) {
                AchievementItem achievement = screen.achievements.items.get(i);
                if (achievement.hb == currentHitbox) {
                    return new AchievementElement(achievement,
                            new CategoryListPosition(i, achievementCount, "achievements"));
                }
            }
            return null;
        }

        private static StatElement getHoveredCharacter(StatsScreen screen) {
            Hitbox hitboxes[] = { screen.allCharsHb, screen.ironcladHb, screen.silentHb, screen.defectHb,
                    screen.watcherHb };
            ArrayList<CharStat> stats = CardCrawlGame.characterManager.getAllCharacterStats();
            stats.add(0, StatsScreen.all);
            int statCount = stats.size();
            for (int h = 0; h < statCount; h++) {
                if (hitboxes[h] == currentHitbox) {
                    String name = "all";
                    if (h > 0) {
                        name = CardCrawlGame.characterManager.getAllCharacters().get(h - 1).title;
                    }
                    return new StatElement(name, stats.get(h));
                }
            }
            return null;
        }

        private static UIElement getHoveredElement(StatsScreen screen) {
            StatElement character = getHoveredCharacter(screen);
            if (character != null)
                return character;
            AchievementElement achievement = getHoveredAchievement(screen);
            if (achievement != null)
                return achievement;
            return null;
        }

        public static void Postfix(StatsScreen __instance) {
            if (__instance.controllerHb != currentHitbox) {
                currentHitbox = __instance.controllerHb;
                UIElement element = getHoveredElement(__instance);
                if (element != null)
                    Output.setUI(element);
            }
        }
    }
}
