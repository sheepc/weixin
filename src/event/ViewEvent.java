package event;

public class ViewEvent extends BaseEvent {
	//事件KEY值，设置的跳转URL
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
