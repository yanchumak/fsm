package fsm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import fsm.context.Context;
import fsm.context.CurrentState;
import fsm.state.State;
import fsm.state.Transition;

final class FiniteStateMachineImpl<E extends Event, C extends Context> implements FiniteStateMachine<E, C> {

	String name;
	FiniteStateMachine<E, C> parent;
	Map<String, FiniteStateMachine<E, C>> children;
	List<State<E, C>> states;
	String initState;

	@Override
	public String initState() {
		return initState;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public List<State<E, C>> states() {
		return new ArrayList<>(states);
	}

	@Override
	public Optional<FiniteStateMachine<E, C>> parent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public List<FiniteStateMachine<E, C>> children() {
		return new ArrayList<>(children.values());
	}

	@Override
	public Optional<FiniteStateMachine<E, C>> childWithName(String finiteStateMachineName) {
		return Optional.ofNullable(children.get(finiteStateMachineName));
	}

	@Override
	public void addChild(FiniteStateMachine<E, C> child) {
		if(children.containsKey(child.name())) {
			throw new IllegalArgumentException(
					String.format("Child finite state machine with name %s is already added", child.name()));
		}
		children.put(child.name(), child);
	}

	@Override
	public void handle(E event, C context) throws FiniteStateMachineException {
		CurrentState currentStateContext = getCurrentStateAndUpdateContext(context);

		State<E,C> currentState = getStateOrThrow(currentStateContext.name());
		try {
			currentState.exitAction().perform(event, context, this);
		} catch(Exception e) {
			throw new FiniteStateMachineException(
					String.format("[%s] Can't perform exit action, state '%s', event '%s'", name(), currentState.name(), event.name()), e);
		}

		Optional<Transition<E, C>> transition = findTransition(currentState, event, context);
		if(transition.isPresent()) {
			State<E, C> nextState = getStateOrThrow(transition.get().nextState());

			try {
				transition.get().action().perform(event, context, this);
			} catch(Exception e) {
				throw new FiniteStateMachineException(
						String.format("[%s] Can't perform action, state '%s', event '%s'", name(), nextState.name(), event.name()), e);
			}

			try {
				nextState.entryAction().perform(event, context, this);
			} catch(Exception e) {
				throw new FiniteStateMachineException(
						String.format("[%s] Can't perform entry action, state '%s', event '%s'", name(), nextState.name(), event.name()), e);
			}

			CurrentState updatedCurrentState = CurrentState.Builder.from(currentStateContext)
					.withName(nextState.name()).build();
			context.updateCurrentState(name(), updatedCurrentState);
		}
	}

	private CurrentState getCurrentStateAndUpdateContext(C context) {
		CurrentState currentStateContext = context.currentStates().get(name());
		if(currentStateContext == null) {
			currentStateContext = CurrentState.builder().withName(initState()).build();
			context.updateCurrentState(name(), currentStateContext);
		}
		return currentStateContext;
	}

	private Optional<Transition<E, C>> findTransition(State<E, C> currentState, E event, C context) throws FiniteStateMachineException {
		return currentState.transitions().stream()
				.filter(t -> t.event().equals(event.name()))
				.filter(t -> t.condition().test(context))
				.findFirst();
	}

	private State<E, C> getStateOrThrow(String state) throws FiniteStateMachineException {
		List<State<E, C>> filteredStates = states.stream()
				.filter(s -> s.name().equals(state))
				.collect(Collectors.toList());
		if(filteredStates.size() == 1) {
			return filteredStates.get(0);
		}
		throw new FiniteStateMachineException(
				String.format("[%s] Expected 1 state with name '%s', but found %d", name(), state, states.size()));
	}
}
