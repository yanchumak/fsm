package fsm.context;

import java.util.HashMap;
import java.util.Map;

final class CurrentStateImpl implements CurrentState {
	private final String name;
	private final Map<String, String> values;

	CurrentStateImpl(String name, Map<String, String> values) {
		this.name = name;
		this.values = values;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Map<String, String> values() {
		return new HashMap<>(values);
	}

	@Override
	public void updateValue(String key, String value) {
		this.values.put(key, value);
	}

	@Override
	public String toString() {
		return "{" +
				"name='" + name + '\'' +
				", values=" + values +
				'}';
	}
}
