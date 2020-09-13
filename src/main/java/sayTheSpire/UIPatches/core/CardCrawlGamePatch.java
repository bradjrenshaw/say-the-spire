import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import sayTheSpire.Output;
import sayTheSpire.TextParser;
import sayTheSpire.events.EventTextEvent;

public class CardCrawlGamePatch {

  @SpirePatch(clz = CardCrawlGame.class, method = "create")
  public static class CardCrawlGameCreatePatch {

    public static void Postfix() {
      Output.setup();
    }
  }

  @SpirePatch(clz = CardCrawlGame.class, method = "update")
  public static class UpdatePatch {

    public static void updateGameEvents() {
      if (Output.eventText != null) {
        Output.event(new EventTextEvent(TextParser.parse(Output.eventText)));
        Output.eventText = null;
      }
    }

    public static void Postfix(CardCrawlGame __instance) {
      Output.update();
      updateGameEvents();
    }
  }
}
    /*
        public static HashMap<InputAction, CInputAction> mappings = new HashMap();


        public static void Postfix() {
            if (mappings.isEmpty()) {
                mappings.put(InputActionSet.confirm, CInputActionSet.select);
                mappings.put(InputActionSet.cancel, CInputActionSet.cancel);
                mappings.put(InputActionSet.up, CInputActionSet.up);
                mappings.put(InputActionSet.down, CInputActionSet.down);
                mappings.put(InputActionSet.left, CInputActionSet.left);
                mappings.put(InputActionSet.right, CInputActionSet.right);
            }
            for (Map.Entry<InputAction, CInputAction> entry:mappings.entrySet()) {
                InputAction keyboard = entry.getKey();
                CInputAction controller = entry.getValue();
                if (keyboard.isJustPressed()) {
                    ReflectionHacks.setPrivate(controller, CInputAction.class, "justPressed", true);
                    ReflectionHacks.setPrivate(controller, CInputAction.class, "pressed", true);
                } else if(keyboard.isPressed()) {
                    ReflectionHacks.setPrivate(controller, CInputAction.class, "justPressed", false);
                    ReflectionHacks.setPrivate(controller, CInputAction.class, "pressed", true);
                } else {
                    ReflectionHacks.setPrivate(controller, CInputAction.class, "pressed", false);
                    ReflectionHacks.setPrivate(controller, CInputAction.class, "pressed", false);
                }
            }
        }
    }

    */
