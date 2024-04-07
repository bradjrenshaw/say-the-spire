package sayTheSpire.utils;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ShowMoveNameAction;

import basemod.ReflectionHacks;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;

public class ActionUtils {

    public static String getShowMoveNameText(ShowMoveNameAction action) {
        String msg = (String) ReflectionHacks.getPrivate(action, ShowMoveNameAction.class, "msg");
        if (msg == null) {
            return OutputUtils.getCreatureName(action.source);
        }
        return Output.localization.localize("text.actions.ShowMoveName", "source",
                OutputUtils.getCreatureName(action.source), "text", msg);
    }

    public static DialogueEvent getTalkEvent(TalkAction action) {
        String msg = (String) ReflectionHacks.getPrivate(action, TalkAction.class, "msg");
        return new DialogueEvent("says", action.source, TextParser.parse(msg, "talk"));
    }
}
