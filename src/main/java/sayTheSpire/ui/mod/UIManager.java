package sayTheSpire.ui.mod;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.megacrit.cardcrawl.core.Settings;
import sayTheSpire.STSConfig;
import sayTheSpire.ui.input.InputAction;
import sayTheSpire.ui.input.InputManager;

public class UIManager {

    private static Logger logger = LogManager.getLogger(UIManager.class.getName());

    private ArrayList<Context> contexts;
    private InputManager inputManager;

    public UIManager(InputManager manager) {
        this.inputManager = manager;
        this.contexts = new ArrayList();
        this.pushContext(new GameContext());
    }

    public void emitAction(InputAction action, String reason) {
        for (Context context : this.contexts) {
            Boolean result = false;
            if (reason.equals("justPressed"))
                result = context.onJustPress(action);
            else if (reason.equals("pressed"))
                result = context.onPress(action);
            else if (reason.equals("justReleased"))
                result = context.onJustRelease(action);
            else
                throw new RuntimeException("Invalud emit action " + reason + " for " + action.getName());
            if (result == true) { // input stopped
                break;
            }
        }
    }

    public Boolean getAllowVirtualInput() {
        Context current = this.getCurrentContext();
        if (current != null) {
            return current.getAllowVirtualInput();
        }
        return false;
    }

    public Context getCurrentContext() {
        if (contexts.size() <= 0) {
            return null;
        }
        return this.contexts.get(0);
    }

    public InputManager getInputManager() {
        return this.inputManager;
    }

    public void pushContext(Context context) {
        if (contexts.size() > 0) {
            contexts.get(0).onUnfocus();
        }
        this.inputManager.clearActionStates();
        this.contexts.add(0, context);
        context.onFocus();
    }

    public void popContext() {
        if (this.contexts.size() > 0) {
            Context context = this.contexts.get(0);
            context.onUnfocus();
            this.contexts.remove(0);
        }
        this.inputManager.clearActionStates();
        if (this.contexts.size() > 0) {
            this.contexts.get(0).onFocus();
        }
    }

    public void updateFirst() {
        Context current = this.getCurrentContext();
        if (current != null) {
            if (!current.getAllowVirtualInput())
                return;
            if (current.getShouldForceControllerMode())
                Settings.isControllerMode = true;
            this.inputManager.updateFirst();
        }
    }

    public void updateLast() {
        Context current = this.getCurrentContext();
        if (current != null) {
            if (!current.getAllowVirtualInput())
                return;
            if (current.getShouldForceControllerMode())
                Settings.isControllerMode = true;
            this.inputManager.updateLast();
        }
    }
}
