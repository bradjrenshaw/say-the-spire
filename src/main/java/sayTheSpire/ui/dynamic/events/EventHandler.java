package sayTheSpire.ui.dynamic.events;

public interface EventHandler<T> {

    public Boolean execute(T event);
}
