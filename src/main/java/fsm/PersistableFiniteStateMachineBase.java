package fsm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fsm.context.Context;
import fsm.state.State;

public class PersistableFiniteStateMachineBase<E extends Event, C extends Context>
		extends FiniteStateMachineImpl<E, C> implements PersistableFiniteStateMachine<E, C> {

	public PersistableFiniteStateMachineBase(String name, List<State<E, C>> states, String initState) {
		super(name, states, initState);
	}

	@Override
	public final void fire(E event, Supplier<C> contextReader, Consumer<C> contextWriter) throws FiniteStateMachineException {
		C context = contextReader.get();
		fire(event, context);
		contextWriter.accept(context);
	}
}
