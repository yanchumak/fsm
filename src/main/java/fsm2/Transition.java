package fsm2;

import java.util.function.Function;

public class Transition {
	final String event;
	final String source;
	final String destination;
	final Function<StateMachineAttributes.Item, StateMachineAttributes.Item> action;


	public Transition(String event, String source, String destination,
	                  Function<StateMachineAttributes.Item, StateMachineAttributes.Item> action) {
		this.event = event;
		this.source = source;
		this.destination = destination;
		this.action = action;
	}

}
