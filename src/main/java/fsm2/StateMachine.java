package fsm2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StateMachine {

	final Map<String, List<Transition>> transitions = new HashMap<>();
	final String name;
	final Map<String, StateMachine> children = new HashMap<>();

	public StateMachine(String name) {
		this.name = name;
	}

	void fireRetry(Signal signal, StateMachineAttributesRepository repository) {
		//retriable
		while(true) {
			fire(signal, repository);
		}
	}

	void fire(Signal signal, StateMachineAttributesRepository repo) {
		StateMachineAttributes s = repo.find().orElseGet(null);//build
		//Event+Source = Unique
		//Retry section begin
		//Read attributes


		transitions.get()

		//commit StateMachineAttributes

		//Retry section end
	}


	String getName() {
		return name;
	}
}
