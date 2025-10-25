package sayTheSpire;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

public class MiscTriggers {

    public static AbstractDungeon.CurrentScreen currentScreen = null;

    public static void handleTreasureRooms(AbstractDungeon.CurrentScreen screen) {
        if (screen != null)
            return;
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null)
            return;
        AbstractChest chest = null;
        if (room instanceof TreasureRoom) {
            chest = ((TreasureRoom) room).chest;
        } else if (room instanceof TreasureRoomBoss) {
            chest = ((TreasureRoomBoss) room).chest;
        }
        if (chest == null)
            return;
        if (chest.isOpen)
            return;
        Hitbox hb = (Hitbox) ReflectionHacks.getPrivate(chest, AbstractChest.class, "hb");
        CInputHelper.setCursor(hb);
    }

    public static void update() {
        AbstractDungeon.CurrentScreen newScreen = null;
        if (AbstractDungeon.isScreenUp) {
            newScreen = AbstractDungeon.screen;
        }
        if (newScreen != currentScreen) {
            onScreenChanged(newScreen);
        }
        currentScreen = newScreen;
    }

    public static void onScreenChanged(AbstractDungeon.CurrentScreen screen) {
        handleTreasureRooms(screen);
    }
}
