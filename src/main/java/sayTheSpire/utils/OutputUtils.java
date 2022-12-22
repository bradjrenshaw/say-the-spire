package sayTheSpire.utils;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.HashMap;
import java.util.stream.Collectors;
import sayTheSpire.Output;

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
            return getPlayerName();
        return creature.name;
    }

    public static String getPlayerName() {
        return CardCrawlGame.playerName;
    }

    public static String getCreaturePowersString(AbstractCreature creature) {
        if (creature.powers.isEmpty())
            return Output.localization.localize("text.powers.no powers");
        String powerList = creature.powers.stream().map(power -> Output.localization.localize("text.powers.powerLabel",
                "power", power.name, "amount", power.amount)).collect(Collectors.joining(", "));
        return Output.localization.localize("text.powers.powerListString", "powers", powerList);
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
