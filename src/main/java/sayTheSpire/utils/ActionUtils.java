package sayTheSpire.utils;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ShowMoveNameAction;
import sayTheSpire.TextParser;
import sayTheSpire.events.DialogueEvent;

public class ActionUtils {

  public static String getShowMoveNameText(ShowMoveNameAction action) {
    String msg = (String) ReflectionHacks.getPrivate(action, ShowMoveNameAction.class, "msg");
    if (msg == null) {
      return action.source.name;
    }
    return action.source.name + ". " + msg;
  }

  public static DialogueEvent getTalkEvent(TalkAction action) {
    String msg = (String) ReflectionHacks.getPrivate(action, TalkAction.class, "msg");
    return new DialogueEvent("says", action.source.name, TextParser.parse(msg, "talk"));
  }
}
