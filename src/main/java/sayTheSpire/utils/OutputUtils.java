package sayTheSpire.utils;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.HashMap;

public class OutputUtils {

    public static Boolean canGetPlayer() {
        return CardCrawlGame.dungeon != null && OutputUtils.isInDungeon() && CardCrawlGame.isInARun();
    }

    public static AbstractPlayer getPlayer() {
        if (!canGetPlayer()) {
            return null;
        }
        return AbstractDungeon.player;
    }

    public static boolean isInDungeon() {
        return CardCrawlGame.mode == CardCrawlGame.GameMode.GAMEPLAY && AbstractDungeon.isPlayerInDungeon();
    }

    public static Boolean isInCombat() {
        return canGetPlayer() && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static String getCreatureName(AbstractCreature creature) {
        if (creature == getPlayer())
            return CardCrawlGame.playerName;
        return creature.name;
    }

    public static String getCreaturePowersString(AbstractCreature creature) {
        if (creature.powers.isEmpty())
            return "No current powers";
        StringBuilder sb = new StringBuilder();
        for (AbstractPower power : creature.powers) {
            sb.append(power.name + ", " + power.amount + " ");
        }
        sb.append("powers");
        return sb.toString();
    }

    public static HashMap<String, String> getValidEnergyTypes() {
        HashMap<String, String> types = new HashMap();
        types.put("R", "red energy");
        types.put("G", "green energy");
        types.put("B", "blue energy");
        types.put("W", "white energy");
        types.put("E", "energy");
        return types;
    }

    public static Boolean playerHasRelic(String relic) {
        AbstractPlayer player = getPlayer();
        if (player == null)
            return false;
        return player.hasRelic(relic);
    }

    public static Boolean playerIsFlying() {
        AbstractPlayer player = getPlayer();
        if (player == null)
            return false;
        return ModHelper.isModEnabled("Flight")
                || (player.hasRelic("WingedGreaves") && player.getRelic("WingedGreaves").counter > 0);
    }
}
