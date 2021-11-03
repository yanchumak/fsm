package fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fsm.context.Context;

final class FiniteStateMachineRegistryImpl<F extends FiniteStateMachine<? extends Event, ? extends Context>>
		implements FiniteStateMachineRegistry<F> {

	private final Map<String, F> eventToFsm;
	private final Map<String, F> nameToFsm = new HashMap<>();

	FiniteStateMachineRegistryImpl(Map<String, F> eventToFsm) {
		this.eventToFsm = eventToFsm;
		eventToFsm.values().forEach(fsm -> nameToFsm.put(fsm.name(), fsm));
	}

	@Override
	public Optional<F> findByEvent(String event) {
		return Optional.ofNullable(eventToFsm.get(event));
	}

	@Override
	public Optional<F> findByName(String name) {
		return Optional.ofNullable(nameToFsm.get(name));
	}

	@Override
	public List<F> findAll() {
		return new ArrayList<>(nameToFsm.values());
	}
}
