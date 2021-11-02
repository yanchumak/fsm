package example;

import fsm.Event;

class MyEvent implements Event {
	final String name;
	final String data;

	MyEvent(String name, String data) {
		this.name = name;
		this.data = data;
	}

	@Override
	public String name() {
		return name;
	}
}