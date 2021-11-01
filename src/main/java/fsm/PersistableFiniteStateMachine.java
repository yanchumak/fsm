package fsm;

import fsm.context.Context;
import fsm.context.ContextRepository;
import fsm.context.ContextRepositoryException;

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
	 * @param contextRepository instance of context repository.
	 *
	 * @return Updated state machine context.
	 *
	 * @throws FiniteStateMachineFireException
	 * @throws ContextRepositoryException
	 */
	C fire(E event, ContextRepository<C> contextRepository) throws FiniteStateMachineFireException, ContextRepositoryException;
}

