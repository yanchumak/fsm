package fsm;

import java.util.function.Consumer;
import java.util.function.Supplier;

import fsm.context.Context;

/**
 * Persistable state machine interface. It is very useful in case of keeping {@link Context} in database or cache.
 *
 * @param <E> the class type of the events.
 * @param <C> the class type of the context.
 */
public interface PersistableFiniteStateMachine<E extends Event, C extends Context> extends FiniteStateMachine<E, C> {

	/**
	 * Fires the specified event. This is how states are transitioned.
	 *
	 * @param event the event fired.
	 * @param contextReader function that reads {@link Context} from database, cache, etc.
	 * @param contextWriter function that save {@link Context} to database, cache, etc.
	 *
	 * @throws FiniteStateMachineException
	 */
	void fire(E event, Supplier<C> contextReader, Consumer<C> contextWriter) throws FiniteStateMachineException;
}

