package fsm;

import java.util.Collection;

import fsm.context.Context;
import fsm.context.ContextRepository;
import fsm.context.ContextRepositoryException;
import fsm.state.State;

public class PersistableFiniteStateMachineBase<E extends Event, C extends Context>
		extends FiniteStateMachineBase<E, C> implements PersistableFiniteStateMachine<E, C> {

	public PersistableFiniteStateMachineBase(String name, Collection<State<E, C>> states, String initState) {
		super(name, states, initState);
	}

	@Override
	public C fire(E event, ContextRepository<C> contextRepository)
			throws FiniteStateMachineFireException, ContextRepositoryException {
		C context = contextRepository.find()
				.orElseThrow(() -> new FiniteStateMachineFireException("Can't find context, event " + event.name()));
		context = fire(event, context);
		contextRepository.save(context);
		return context;
	}
}
