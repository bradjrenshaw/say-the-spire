package sayTheSpire.speech;

import java.util.ArrayList;

/** The SpeechManager handles abstracting out screen reader access. */
public class SpeechManager {

    private ArrayList<SpeechHandler> handlers;
    private SpeechHandler currentHandler;

    public SpeechManager() {
        this.handlers = new ArrayList();
        this.currentHandler = null;
    }

    public void disposeResources() {
        if (this.currentHandler == null)
            return;
        this.currentHandler.disposeResources();
    }

    /**
     * Outputs text to the current speech handler.
     *
     * @param text
     *            The text to be output
     * @param interrupt
     *            Whether or not previous speech/braille/etc should be interrupted.
     * 
     * @return True on success, false otherwise
     */
    public Boolean output(String text, Boolean interrupt) {
        if (this.currentHandler == null)
            return false;
        return this.currentHandler.output(text, interrupt);
    }

    /**
     * Registers a handler for detection and use
     *
     * @param handler
     *            The SpeechHandler to register
     * 
     * @return true if registered, false if the handler is already registered
     */
    public Boolean registerHandler(SpeechHandler handler) {
        if (this.handlers.contains(handler))
            return false;
        this.handlers.add(handler);
        return true;
    }

    /**
     * Determines which speech handler to use. Call this after all handlers are registered.
     *
     * @return True if a handler was successfully loaded, false otherwise.
     */
    public Boolean setup() {
        for (SpeechHandler handler : this.handlers) {
            if (!handler.detect())
                continue;
            if (!handler.loadResources())
                continue;
            if (!handler.load())
                continue;
            this.currentHandler = handler;
            return true;
        }
        return false;
    }

    /** Stops any current speech. */
    public Boolean silence() {
        if (this.currentHandler == null)
            return false;
        return this.currentHandler.silence();
    }

    /**
     * Unloads the current speech handler and disposes resources.
     *
     * @return True on success, false otherwise.
     */
    public Boolean unload() {
        if (this.currentHandler == null)
            return false;
        this.currentHandler.unload();
        return true;
    }

    /**
     * Unregisters a given handler.
     *
     * @param handler
     *            The handler to be unregistered
     * 
     * @return True if the handler was found and removed, false otherwise
     */
    public boolean unregisterHandler(SpeechHandler handler) {
        if (!this.handlers.contains(handler))
            return false;
        this.handlers.remove(handler);
        return true;
    }
}
