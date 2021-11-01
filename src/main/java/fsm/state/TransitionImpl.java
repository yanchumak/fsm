package fsm.state;

import fsm.Event;
import fsm.context.Context;

class TransitionImpl<E extends Event, C extends Context> implements Transition<E, C> {
	String event;
	String nextState;
	Action<E, C> action = Action.noAction();
	Condition<C> condition = Condition.noCondition();

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
