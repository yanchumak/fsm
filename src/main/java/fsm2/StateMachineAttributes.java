package fsm2;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StateMachineAttributes {

	List<Item> getAttributes();

	default Optional<Item> findFor(String stateMachineName) {
		return getAttributes().stream().filter(s -> s.getName().equals(stateMachineName)).findFirst();
	}

	interface Item {
		String getName();
		String getState();
		Map<String, String> getAdditional();
	}
}
