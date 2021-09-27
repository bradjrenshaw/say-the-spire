package sayTheSpire.speech;

import com.davykager.tolk.Tolk;

public class TolkHandler extends SpeechHandler {

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
        Tolk.speak(text, interrupt);
        return true;
    }

    public void unload() {
        Tolk.unload();
    }
}
