package fsm;

import java.util.Collection;

import fsm.context.Context;
import fsm.state.State;

/**
 * The operational state machine interface.
 *
 * @param <E> the class type of the events.
 * @param <C> the class type of the context.
 */
public interface FiniteStateMachine<E extends Event, C extends Context> {

	/**
	 * It returns name of init state of the finite state machine.
	 *
	 * @return name of init state.
	 */
	String initState();

	/**
	 * Name of the current state machine.
	 *
	 * @return state machine name.
	 */
	String name();

	/**
	 * Collection of allowed states of the current state machine.
	 *
	 * @return collection of {@link State}
	 */
	Collection<State<E, C>> states();

	/**
	 * Fires the specified event. This is how states are transitioned.
	 *
	 * @param event the event fired.
	 * @param context the context of the current state machine.
	 *
	 * @return Updated state machine context.
	 *
	 * @throws FiniteStateMachineFireException
	 */
	C fire(E event, C context) throws FiniteStateMachineFireException;

}
