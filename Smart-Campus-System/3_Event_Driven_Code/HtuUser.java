package Event_driven;

public class HtuUser {

	private String fullName;
	private int userCardId;

	public HtuUser(String fullName, int userCardId) {
		this.fullName = fullName;
		this.userCardId = userCardId;
	}

	public String getFullName() {
		return fullName;
	}

	public int getUserCardId() {
		return userCardId;
	}
}
