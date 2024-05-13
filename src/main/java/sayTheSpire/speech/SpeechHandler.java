package sayTheSpire.speech;

import sayTheSpire.Output;
import sayTheSpire.ui.IUIInfo;

/**
 * A SpeechHandler represents some form of accessibility output (screen reader library, multiple libraries in one
 * (Tolk), etc)
 */
public abstract class SpeechHandler implements IUIInfo {

    private String key;

    public SpeechHandler() {
        this.setKey(null);
    }

    public String getKey() {
        return this.key;
    }

    public String getLabel() {
        return Output.localization.localize("ui.speech handlers." + this.getKey() + ".label");
    }

    protected void setKey(String key) {
        this.key = key;
    }

    /**
     * Detect is called and determines whether or not this handler can even be used.
     *
     * @return True if the handler can be used, false otherwise
     */
    public abstract Boolean detect();

    /** Disposes any loaded resources when the game is closed. */
    public abstract void disposeResources();

    /**
     * After resources are loaded, this method performs any additional required steps, such as library loading.
     */
    public abstract Boolean load();

    /**
     * This method requires setting up any resources needed, such as moving dll files to the correct location.
     *
     * @return True on success, false otherwise.
     */
    public abstract Boolean loadResources();

    /**
     * Output sends any text to be comunicated to the user. This leaves the handling up to the handler (for example
     * Output will generally both speak and braille text simultaneously for screen reader libs).
     *
     * @param text
     *            The text to be sent
     * @param interrupt
     *            Whether or not this should interrupt text currently being spoken/brailled/etc.
     * 
     * @return True on success, false otherwise
     */
    public abstract Boolean output(String text, Boolean interrupt);

    /**
     * Speaks the given text.
     *
     * @param text
     *            The text to be sent
     * @param interrupt
     *            Whether or not this should interrupt text currently being spoken.
     * 
     * @return True on success, false otherwise
     */
    public abstract Boolean speak(String text, Boolean interrupt);

    /** Silence currently spoken text. */
    public abstract Boolean silence();

    /**
     * Performs any needed steps to unload the library before unloading resources, such as unloading libraries.
     */
    public abstract void unload();
}
