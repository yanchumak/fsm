package fsm.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Current state of particular state machine.
 */
public interface CurrentState {

	/**
	 * The current state name.
	 *
	 * @return name of state.
	 */
	String name();

	//TODO update/remove/get value?
	/**
	 * The method returns copy of finite state machine values.
	 *
	 * @return copied values of state machine.
	 */
	Map<String, String> values();

	/**
	 * The method updates value by key.
	 *
	 * @param name of value.
	 * @param value new value.
	 */
	void updateValue(String name, String value);

	class Builder {
		private String name;
		private Map<String, String> values = new HashMap<>();

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder addValue(String key, String value) {
			this.values.put(key, value);
			return this;
		}

		public Builder withValues(Map<String, String> values) {
			Objects.requireNonNull(values, "values can't be null");
			this.values = new HashMap<>(values);
			return this;
		}

		public CurrentState build() {
			Objects.requireNonNull(name, "name can't null");
			return new CurrentStateImpl(name, values);
		}

		public static Builder from(CurrentState currentState) {
			Builder builder = new Builder();
			builder.name = currentState.name();
			builder.values = currentState.values();
			return builder;
		}
	}

	static CurrentState.Builder builder() {
		return new CurrentState.Builder();
	}
}