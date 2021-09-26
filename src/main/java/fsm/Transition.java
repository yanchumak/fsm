package fsm;

/**
 * Simple interface that represents a transition between states.
 *
 * @param <S> the class type of the states.
 * @param <C> the class type of the context.
 */
public interface Transition<E, S, C> {

    /**
     * The state the transition originates from.
     *
     * @return the source state.
     */
    S getSource();

    /**
     * The state the transition completes at.
     *
     * @return the destination state.
     */
    S getDestination();

    /**
     * Returns the action to perform during the transition.  If the transition does not of an action, a transition
     * action that performs no action should be returned.
     *
     * @return the transition action.
     */
    Action<C> getAction();

    E getTrigger();

}
