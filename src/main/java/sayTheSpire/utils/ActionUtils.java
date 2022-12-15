package sayTheSpire.utils;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ShowMoveNameAction;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;
import sayTheSpire.Output;

public class ActionUtils {

    public static String getShowMoveNameText(ShowMoveNameAction action) {
        String msg = (String) ReflectionHacks.getPrivate(action, ShowMoveNameAction.class, "msg");
        if (msg == null) {
            return action.source.name;
        }
        return Output.localization.localize("text.actions.ShowMoveName", "source", action.source.name, "text", msg);
    }

    public static DialogueEvent getTalkEvent(TalkAction action) {
        String msg = (String) ReflectionHacks.getPrivate(action, TalkAction.class, "msg");
        return new DialogueEvent("says", action.source, TextParser.parse(msg, "talk"));
    }
}
