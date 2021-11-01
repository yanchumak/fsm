package fsm.state;

import fsm.Event;
import fsm.FiniteStateMachine;
import fsm.context.Context;

/**
 * Functional interface that represents an action that should be performed.  Actions are performed when a state is
 * entered, exited or between states transition .
 *
 * @param <E> the class type of the event.
 * @param <C> the class type of the context.
 */
@FunctionalInterface
public interface Action<E extends Event, C extends Context> {

	/**
	 * Action that will be performed on
	 *      {@link fsm.state.State#entryAction()}
	 *      {@link fsm.state.State#exitAction()}
	 *      {@link fsm.state.Transition#action()}
	 *
	 * @param context of {@link FiniteStateMachine}
	 *
	 * @return context of {@link FiniteStateMachine}
	 */
	C perform(E event, C context);

	/**
	 * Action that does absolutely nothing.
	 */
	static <E extends Event, C extends Context> Action<E, C> noAction() {
		return (e, c) -> c;
	}
}