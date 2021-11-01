package fsm.state;

import java.util.List;

import fsm.Event;
import fsm.context.Context;

final class StateImpl<E extends Event, C extends Context> implements State<E,C> {
	private final String name;
	private final List<Transition<E, C>> transitions;
	private final Action<E, C> entryAction;
	private final Action<E, C> exitAction;

	StateImpl(String name, List<Transition<E, C>> transitions, Action<E, C> entryAction, Action<E, C> exitAction) {
		this.name = name;
		this.transitions = transitions;
		this.entryAction = entryAction;
		this.exitAction = exitAction;
	}

	@Override
	public Action<E, C> entryAction() {
		return entryAction;
	}

	@Override
	public Action<E, C> exitAction() {
		return exitAction;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public List<Transition<E, C>> transitions() {
		return transitions;
	}
}
