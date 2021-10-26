package fsm2;

import java.util.Optional;

public interface StateMachineAttributesRepository {

	StateMachineAttributes save(StateMachineAttributes attributes);

	Optional<StateMachineAttributes> find();

}
