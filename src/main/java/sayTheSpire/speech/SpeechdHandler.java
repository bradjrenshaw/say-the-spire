package sayTheSpire.speech;

import speechd.ssip.SSIPException;
import speechd.ssip.SSIPClient;
import speechd.ssip.SSIPPriority;
import sayTheSpire.Output;

public class SpeechdHandler extends SpeechHandler {

    SSIPClient spd = null;

    public SpeechdHandler() {
        super();
        this.setName("speech-dispatcher");
    }

    public Boolean detect() {
        return System.getProperty("os.name").startsWith("Linux");
    }

    public void disposeResources() {
        // do nothing
    }

    public Boolean loadResources() {
        return true; // do nothing
    }

    public Boolean load() {
        try {
            spd = new SSIPClient("sayTheSpire", null, null);
        } catch (SSIPException e) {
            return false;
        }
        return true;
    }

    public Boolean output(String text, Boolean interrupt) {
        try {
            if (interrupt == true) {
                spd.say(SSIPPriority.IMPORTANT, text);
            } else {
                spd.say(SSIPPriority.MESSAGE, text);
            }
        } catch (SSIPException e) {
            return false;
        }
        return true;
    }

    public Boolean silence() {
        try {
            spd.stop();
        } catch (SSIPException e) {
            return false;
        }
        return true;
    }

    public Boolean speak(String text, Boolean interrupt) {
        try {
            if (interrupt == true) {
                spd.say(SSIPPriority.IMPORTANT, text);
            } else {
                spd.say(SSIPPriority.MESSAGE, text);
            }
        } catch (SSIPException e) {
            return false;
        }
        return true;
    }

    public void unload() {
        try {
            spd.close();
        } catch (SSIPException e) {
            spd = null;
            return;
        }
        spd = null;
    }
}
