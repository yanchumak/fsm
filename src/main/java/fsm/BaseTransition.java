package fsm;

public class BaseTransition<E, S, C> implements Transition<E, S, C> {

    private S source;
    private S destination;
    private Action<C> action;
    private E trigger;

    public BaseTransition() {

    }

    public BaseTransition(S source, S destination, Action<C> action, E trigger) {
        this.source = source;
        this.destination = destination;
        this.action = action;
        this.trigger = trigger;
    }

    public void setSource(S source) {
        this.source = source;
    }

    public void setDestination(S destination) {
        this.destination = destination;
    }

    public void setAction(Action<C> action) {
        this.action = action;
    }

    public void setTrigger(E trigger) {
        this.trigger = trigger;
    }

    @Override
    public S getSource() {
        return source;
    }

    @Override
    public S getDestination() {
        return destination;
    }

    @Override
    public Action<C> getAction() {
        return action;
    }

    @Override
    public E getTrigger() {
        return trigger;
    }
}
