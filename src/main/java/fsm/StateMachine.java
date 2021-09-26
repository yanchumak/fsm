package fsm;


import java.util.Optional;

/**
 * The operational state machine interface.
 *
 * @param <S> the class type of the states.
 * @param <E> the class type of the events.
 * @param <C> the class type of the context.
 */
public interface StateMachine<S, E, C> {

    /**
     * Returns the context of the state machine.
     *
     * @return the context.
     */
    C getContext();

    /**
     * Returns the current state of the state machine.
     *
     * @return the current state.
     */
    S getState();

    /**
     * Fires the specified event.  This is how states are transitioned.
     *
     * @param event the event fired.
     * @return the resulting transition.
     */
    Optional<Transition<E, S, C>> fire(E event);

    /**
     * Adds transition for specified event.
     *
     * @param event transition trigger
     * @param transition transition
     */
    void addTransition(E event, Transition<E, S, C> transition);
}
