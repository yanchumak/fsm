package fsm.state;

import java.util.ArrayList;
import java.util.List;

import fsm.Event;
import fsm.context.Context;

class StateImpl<E extends Event, C extends Context> implements State<E,C> {
	String name;
	List<Transition<E, C>> transitions = new ArrayList<>();
	Action<E, C> entryAction = Action.noAction();
	Action<E, C> exitAction = Action.noAction();

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
