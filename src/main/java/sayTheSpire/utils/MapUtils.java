package sayTheSpire.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.map.BaseRoomNode;

public class MapUtils {

    public static String getMonsterName(String name) {
        return CardCrawlGame.languagePack.getMonsterStrings(name).NAME;
    }

    public static String getLocalizedBossName() {
        if (AbstractDungeon.bossList == null || AbstractDungeon.bossList.isEmpty())
            return "unknown";
        String name = AbstractDungeon.bossList.get(0);
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
        default:
            return name;
        }
    }

    public static MapRoomNode getCurrentNode() {
        return AbstractDungeon.getCurrMapNode();
    }

    public static Boolean isBossAvailable(BaseRoomNode node) {
        return node.getY() == AbstractDungeon.map.size() - 1;
    }
}
