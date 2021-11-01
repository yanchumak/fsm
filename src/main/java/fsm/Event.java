package fsm;


/**
 * The event that triggers transition between states.
 */
public interface Event {

	/**
	 * Name of the event.
	 * @return event name.
	 */
	String name();
}
