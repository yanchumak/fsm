package fsm;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fsm.context.Context;
import fsm.state.State;

public class PersistableFiniteStateMachineBase<E extends Event, C extends Context> implements PersistableFiniteStateMachine<E, C> {

	private final FiniteStateMachine<E, C> delegate;

	public PersistableFiniteStateMachineBase(String name, List<State<E, C>> states, String initState) {
		this.delegate = FiniteStateMachine.<E, C>builder()
				.withName(name)
				.withStates(states)
				.withInitState(initState).build();
	}

	@Override
	public final void handle(E event, Supplier<C> contextReader, Consumer<C> contextWriter) throws FiniteStateMachineException {
		//TODO add handling optimistic lock
		C context = contextReader.get();
		handle(event, context);
		contextWriter.accept(context);
	}

	@Override
	public String initState() {
		return delegate.initState();
	}

	@Override
	public String name() {
		return delegate.name();
	}

	@Override
	public List<State<E, C>> states() {
		return delegate.states();
	}

	@Override
	public Optional<FiniteStateMachine<E, C>> parent() {
		return delegate.parent();
	}

	@Override
	public List<FiniteStateMachine<E, C>> children() {
		return delegate.children();
	}

	@Override
	public Optional<FiniteStateMachine<E, C>> childWithName(String finiteStateMachineName) {
		return delegate.childWithName(finiteStateMachineName);
	}

	@Override
	public void addChild(FiniteStateMachine<E, C> child) {
		delegate.addChild(child);
	}

	@Override
	public void handle(E event, C context) throws FiniteStateMachineException {
		delegate.handle(event, context);
	}
}
