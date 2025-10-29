package sayTheSpire.utils;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import downfall.patches.EvilModeCharacterSelect;
import sayTheSpire.map.BaseRoomNode;

public class MapUtils {

    public static String getMonsterName(String name) {
        return CardCrawlGame.languagePack.getMonsterStrings(name).NAME;
    }

    public static String getLocalizedBossName() {
        if (AbstractDungeon.bossList == null || AbstractDungeon.bossList.isEmpty())
            return "unknown";
        String name = AbstractDungeon.bossKey;
        switch (name) {
        case "The Guardian":
            return getMonsterName("TheGuardian");
        case "Hexaghost":
            return getMonsterName("Hexaghost");
        case "Slime Boss":
            return getMonsterName("SlimeBoss");
        case "Collector":
            return getMonsterName("TheCollector");
        case "Automaton":
            return getMonsterName("BronzeAutomaton");
        case "Champ":
            return getMonsterName("Champ");
        case "Awakened One":
            return getMonsterName("AwakenedOne");
        case "Time Eater":
            return getMonsterName("TimeEater");
        case "Donu and Deca":
            return getMonsterName("Donu") + ", " + getMonsterName("Deca");
        case "The Heart":
            return getMonsterName("CorruptHeart");
        case "downfall:Silent":
            try {
                Class<?> cls = Class.forName("charbosses.bosses.Silent.CharBossSilent");
                return ReflectionHacks.getPrivateStatic(cls, "NAME");
            } catch (Throwable ignored) {
                return "Unknown, report to developer";
            }
        case "downfall:Defect":
            try {
                Class<?> cls = Class.forName("charbosses.bosses.Defect.CharBossDefect");
                return ReflectionHacks.getPrivateStatic(cls, "NAME");
            } catch (Throwable ignored) {
                return "Unknown, report to developer";
            }
        case "downfall:Watcher":
            try {
                Class<?> cls = Class.forName("charbosses.bosses.Watcher.CharBossWatcher");
                return ReflectionHacks.getPrivateStatic(cls, "NAME");
            } catch (Throwable ignored) {
                return "Unknown, report to developer";
            }
        case "downfall:Hermit":
            try {
                Class<?> cls = Class.forName("charbosses.bosses.Hermit.CharBossHermit");
                return ReflectionHacks.getPrivateStatic(cls, "NAME");
            } catch (Throwable ignored) {
                return "Unknown, report to developer";
            }
        default:
            return "Unknown, report to developer";
        }
    }

    public static MapRoomNode getCurrentNode() {
        return AbstractDungeon.getCurrMapNode();
    }

    public static Boolean isBossAvailable(BaseRoomNode node) {
        if (Loader.isModLoaded("downfall") && EvilModeCharacterSelect.evilMode) {
            return node.getY() == 0;
        } else {
            return node.getY() == AbstractDungeon.map.size() - 1;
        }
    }
}
