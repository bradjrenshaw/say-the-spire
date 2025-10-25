package sayTheSpire.speech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sayTheSpire.Output;

/** The SpeechManager handles abstracting out screen reader access. */
public class SpeechManager {
    private static Logger logger = LogManager.getLogger(Output.class.getName());

    private ArrayList<SpeechHandler> handlers;
    private SpeechHandler currentHandler;

    public SpeechManager() {
        this.handlers = new ArrayList();
        this.currentHandler = null;
        this.addHandlers();
    }

    private void addHandlers() {
        this.registerHandler(new TolkResourceHandler());
        this.registerHandler(new TolkHandler());
        this.registerHandler(new SpeechdHandler());
        this.registerHandler(new ClipboardHandler());
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
        logger.info("Registered speech handler " + handler.getName());
        return true;
    }

    private void reorderHandlerList() {
        ArrayList<String> preferedOrder = (ArrayList<String>) Output.config
                .getList("advanced.prefered_speech_handler_order", null);
        if (preferedOrder == null || preferedOrder.size() == 0)
            return;
        HashMap<String, SpeechHandler> reorderedHandlers = new HashMap();
        ListIterator iter = this.handlers.listIterator();
        while (iter.hasNext()) {
            SpeechHandler handler = (SpeechHandler) iter.next();
            if (preferedOrder.contains(handler.getName())) {
                iter.remove();
                reorderedHandlers.put(handler.getName(), handler);
            }
        }
        iter = preferedOrder.listIterator(preferedOrder.size());
        while (iter.hasPrevious()) {
            String name = (String) iter.previous();
            this.handlers.add(0, reorderedHandlers.get(name));
        }
    }

    /**
     * Determines which speech handler to use. Call this after all handlers are registered.
     *
     * @return True if a handler was successfully loaded, false otherwise.
     */
    public Boolean setup() {
        logger.info("Setting up speech handlers.");
        this.reorderHandlerList();
        for (SpeechHandler handler : this.handlers) {
            if (!handler.detect()) {
                logger.info("Speech handler " + handler.getName() + " detect returned false.");
                continue;
            }
            if (!handler.loadResources()) {
                logger.info("Speech handler " + handler.getName() + " failed to load resources");
                continue;
            }
            if (!handler.load()) {
                logger.info("Speech handler " + handler.getName() + " failed to load.");
                continue;
            }
            this.currentHandler = handler;
            logger.info("Speech handler " + handler.getName() + " successfully loaded.");
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
