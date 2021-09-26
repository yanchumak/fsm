package fsm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BaseStateMachine<S, E, C> implements StateMachine<S, E, C> {
    private final Map<E, Transition<E, S, C>> transitions = new HashMap<>();
    private S state;

    public BaseStateMachine(S state) {
        this.state = state;
    }
    @Override
    public C getContext() {
        return null;
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public Optional<Transition<E, S, C>> fire(E event) {
        Transition<E, S, C> transition = transitions.get(event);
        if (transition == null) {
            return Optional.empty();
        }

        state = transition.getDestination();

        return Optional.of(transition);
    }

    @Override
    public void addTransition(E event, Transition<E, S, C> transition) {
        transitions.put(event, transition);
    }
}
