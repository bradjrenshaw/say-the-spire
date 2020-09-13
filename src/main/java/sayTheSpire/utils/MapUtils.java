package sayTheSpire.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import sayTheSpire.mapNavigator.FakeBossNode;
import sayTheSpire.mapNavigator.FakeMapNode;

public class MapUtils {

  public static MapRoomNode getCurrentNode() {
    MapRoomNode current = AbstractDungeon.getCurrMapNode();
    if (current == null) {
      return new FakeMapNode(-1, -1);
    }
    return current;
  }

  public static String getMonsterName(String name) {
    return CardCrawlGame.languagePack.getMonsterStrings(name).NAME;
  }

  public static String getLocalizedBossName() {
    if (AbstractDungeon.bossList == null || AbstractDungeon.bossList.isEmpty()) return "unknown";
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
        return "Unknown, report to developer";
    }
  }

  public static String getMapNodeShort(MapRoomNode node) {
    String extra = getNodeExtra(node);
    return getRoomTypeString(node) + extra + ":" + node.x + ", " + node.y;
  }

  public static String getNodeExtra(MapRoomNode node) {
    if (node.hasEmeraldKey) {
      return " (burning icon)";
    }
    return "";
  }

  public static String getRoomTypeString(MapRoomNode node) {
    if (node instanceof FakeBossNode) {
      FakeBossNode bossNode = (FakeBossNode) node;
      return "boss (" + bossNode.getBossName() + ")";
    } else if (node.room instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) {
      return "elite monster";
    } else if (node.room instanceof com.megacrit.cardcrawl.rooms.MonsterRoom) {
      return "Monster";
    } else if (node.room instanceof com.megacrit.cardcrawl.rooms.ShopRoom) {
      return "merchant";
    } else if (node.room instanceof com.megacrit.cardcrawl.rooms.RestRoom) {
      return "rest";
    } else if (node.room instanceof com.megacrit.cardcrawl.rooms.TreasureRoom) {
      return "treasure";
    } else {
      return "unknown";
    }
  }

  public static Boolean isBossAvailable(MapRoomNode node) {
    return node.y == AbstractDungeon.map.size() - 1;
  }
}
