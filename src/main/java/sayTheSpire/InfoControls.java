package sayTheSpire;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;

public class InfoControls {

    public enum Direction {
        NONE, UP, DOWN, LEFT, RIGHT
    };

    public static String bufferContext = "";

    public static void infoControls(Direction direction) {
        if (bufferContext.equals(""))
            return;
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP && bufferContext.equals("map")) {
            mapInfoControls(direction);
        } else {
            bufferInfoControls(direction);
        }
    }

    public static void bufferInfoControls(Direction direction) {
        switch (direction) {
        case UP:
            BufferControls.nextItem();
            break;
        case DOWN:
            BufferControls.previousItem();
            break;
        case LEFT:
            BufferControls.previousBuffer();
            break;
        case RIGHT:
            BufferControls.nextBuffer();
            break;
        }
    }

    public static void mapInfoControls(Direction direction) {
        Output.mapManager.control(direction);
    }

    public static Direction getInfoDirection() {
        if (CInputActionSet.inspectUp.isJustPressed())
            return Direction.UP;
        else if (CInputActionSet.inspectDown.isJustPressed())
            return Direction.DOWN;
        else if (CInputActionSet.inspectLeft.isJustPressed())
            return Direction.LEFT;
        else if (CInputActionSet.inspectRight.isJustPressed())
            return Direction.RIGHT;
        else
            return Direction.NONE;
    }

    public static void update() {
        if (CInputActionSet.inspectUp.isJustPressed()) {
            infoControls(Direction.UP);
        } else if (CInputActionSet.inspectRight.isJustPressed()) {
            infoControls(Direction.RIGHT);
        } else if (CInputActionSet.inspectDown.isJustPressed()) {
            infoControls(Direction.DOWN);
        } else if (CInputActionSet.inspectLeft.isJustPressed()) {
            infoControls(Direction.LEFT);
        }
    }
}
