package sayTheSpire.speech;

import com.davykager.tolk.Tolk;
import sayTheSpire.Output;

public class TolkHandler extends SpeechHandler {

    public TolkHandler() {
        super();
        this.setName("tolk");
    }

    public Boolean detect() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public void disposeResources() {
        // do nothing
    }

    public Boolean loadResources() {
        return true; // do nothing
    }

    public Boolean load() {
        Tolk.load();
        Tolk.preferSAPI(Output.config.getBoolean("advanced.speech_handler_force_system_speech"));
        return true;
    }

    public Boolean output(String text, Boolean interrupt) {
        Tolk.trySAPI(true);
        Tolk.output(text, interrupt);
        return true;
    }

    public Boolean silence() {
        Tolk.silence();
        return true;
    }

    public Boolean speak(String text, Boolean interrupt) {
        Tolk.trySAPI(true);
        Tolk.speak(text, interrupt);
        return true;
    }

    public void unload() {
        Tolk.unload();
    }
}
