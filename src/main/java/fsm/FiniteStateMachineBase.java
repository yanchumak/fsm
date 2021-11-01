package fsm;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fsm.context.Context;
import fsm.context.CurrentState;
import fsm.state.State;
import fsm.state.Transition;

public class FiniteStateMachineBase<E extends Event, C extends Context> implements FiniteStateMachine<E, C> {

	private final String name;
	private final Collection<State<E, C>> states;
	private final String initState;

	public FiniteStateMachineBase(String name, Collection<State<E, C>> states, String initState) {
		this.name = name;
		this.states = states;
		this.initState = initState;
	}

	@Override
	public String initState() {
		return initState;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Collection<State<E, C>> states() {
		return states;
	}

	@Override
	public C fire(E event, C context) throws FiniteStateMachineFireException {
		CurrentState currentStateContext;
		Optional<CurrentState> currentStateContextOpt = context.findFor(name());
		if(currentStateContextOpt.isPresent()) {
			currentStateContext = currentStateContextOpt.get();
		} else {
			currentStateContext = CurrentState.of(initState(), name());
			context.currentStates().add(currentStateContext);
		}

		State<E,C> currentState = getStateOrThrow(currentStateContext.name());

		try {
			context = currentState.exitAction().perform(event, context);
		} catch(Exception e) {
			throw new FiniteStateMachineFireException(
					String.format("[%s] Can't perform exit action, state '%s', event '%s'", name(), currentState.name(), event.name()), e);
		}

		Transition<E, C> transition = getTransitionOrThrow(currentState, event, context);
		State<E, C> nextState = getStateOrThrow(transition.nextState());

		try {
			context = nextState.entryAction().perform(event, context);
		} catch(Exception e) {
			throw new FiniteStateMachineFireException(
					String.format("[%s] Can't perform entry action, state '%s', event '%s'", name(), nextState.name(), event.name()), e);
		}

		try {
			context = transition.action().perform(event, context);
		} catch(Exception e) {
			throw new FiniteStateMachineFireException(
					String.format("[%s] Can't perform action, state '%s', event '%s'", name(), nextState.name(), event.name()), e);
		}
		context.currentStates().clear();
		context.currentStates().add(CurrentState.of(nextState.name(), name()));
		return context;
	}

	private Transition<E, C> getTransitionOrThrow(State<E, C> currentState, E event, C context) throws FiniteStateMachineFireException {
		List<Transition<E, C>> transitions = currentState.transitions().stream()
				.filter(t -> t.event().equals(event.name()))
				.filter(t -> t.condition().test(context))
				.collect(Collectors.toList());
		if(transitions.size() == 1) {
			return transitions.get(0);
		}
		throw new FiniteStateMachineFireException(
				String.format("[%s] Expected 1 transition with state '%s' and event '%s', but found %d",
						name(), currentState.name(), event.name(), transitions.size()));
	}

	private State<E, C> getStateOrThrow(String state) throws FiniteStateMachineFireException {
		List<State<E, C>> states = states().stream()
				.filter(s -> s.name().equals(state))
				.collect(Collectors.toList());
		if(states.size() == 1) {
			return states.get(0);
		}
		throw new FiniteStateMachineFireException(
				String.format("[%s] Expected 1 state with name '%s', but found %d", name(), state, states.size()));
	}
}
