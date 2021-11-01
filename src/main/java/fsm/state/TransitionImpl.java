package fsm.state;

import fsm.Event;
import fsm.context.Context;

final class TransitionImpl<E extends Event, C extends Context> implements Transition<E, C> {
	private final String event;
	private final String nextState;
	private final Action<E, C> action;
	private final Condition<C> condition;

	TransitionImpl(String event, String nextState, Action<E, C> action, Condition<C> condition) {
		this.event = event;
		this.nextState = nextState;
		this.action = action;
		this.condition = condition;
	}

	@Override
	public String event() {
		return event;
	}

	@Override
	public String nextState() {
		return nextState;
	}

	@Override
	public Action<E, C> action() {
		return action;
	}

	@Override
	public Condition<C> condition() {
		return condition;
	}
}
