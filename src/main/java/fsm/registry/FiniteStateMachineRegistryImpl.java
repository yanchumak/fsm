package fsm.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fsm.Event;
import fsm.FiniteStateMachine;
import fsm.context.Context;

final class FiniteStateMachineRegistryImpl<E extends Event, C extends Context> implements FiniteStateMachineRegistry<E, C> {

	private final Map<String, FiniteStateMachine<E, C>> eventToFsm;
	private final Map<String, FiniteStateMachine<E, C>> nameToFsm = new HashMap<>();

	FiniteStateMachineRegistryImpl(Map<String, FiniteStateMachine<E, C>> eventToFsm) {
		this.eventToFsm = eventToFsm;
		eventToFsm.values().forEach(fsm -> nameToFsm.put(fsm.name(), fsm));
	}

	@Override
	public Optional<FiniteStateMachine<E, C>> findByEvent(String event) {
		return Optional.ofNullable(eventToFsm.get(event));
	}

	@Override
	public Optional<FiniteStateMachine<E, C>> findByName(String name) {
		return Optional.ofNullable(nameToFsm.get(name));
	}

	@Override
	public List<FiniteStateMachine<E, C>> findAll() {
		return new ArrayList<>(nameToFsm.values());
	}
}
