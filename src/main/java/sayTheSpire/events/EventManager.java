package sayTheSpire.events;

import java.util.ArrayList;
import java.util.ListIterator;
import sayTheSpire.Output;

/** The event manager handles pending and completed events. */
public class EventManager {

    public static ArrayList<Event> events = new ArrayList();

    /** Adds an event to the pending queue. */
    public static void add(Event event) {
        events.add(event);
        process();
    }

    /** Handles all events in the pending queue, removing or completing events as needed. */
    public static void process() {
        ListIterator iter = events.listIterator();
        while (iter.hasNext()) {
            Event event = (Event) iter.next();
            event.update();
            if (event.shouldAbandon()) {
                iter.remove();
            } else if (event.isComplete()) {
                iter.remove();
                queueForLog(event);
            }
        }
    }

    /** Queues a completed event for the completed log and outputs the event's text to the user. */
    public static void queueForLog(Event event) {
        if (event == null)
            return;
        Output.buffers.getBuffer("events").add(event.getText());
        Output.text(event.getText(), false);
    }

    /** Called every frame. Handles all event processing needed. */
    public static void update() {
        process();
    }
}
