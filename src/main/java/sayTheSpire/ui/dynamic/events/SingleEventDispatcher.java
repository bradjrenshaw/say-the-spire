package sayTheSpire.ui.dynamic.events;

import java.util.ArrayList;

public class SingleEventDispatcher<T extends DynamicEvent> {

    private ArrayList<EventHandler<T>> eventHandlers;

    public SingleEventDispatcher() {
        this.eventHandlers = new ArrayList();
    }

    public void dispatch(T event) {
        for (EventHandler<T> handler : this.eventHandlers) {
            // stop event propigation if an execute call returns true
            if (handler.execute(event)) {
                return;
            }
        }
    }

    public void registerHandler(EventHandler<T> handler) {
        this.eventHandlers.add(handler);
    }

}
