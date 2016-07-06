package services.login;

public class LoginTicket {
	public String ticket;
	public int duration;
	
	public LoginTicket() {
	}

	public LoginTicket(String ticket, int duration) {
		super();
		this.ticket = ticket;
		this.duration = duration;
	}
}
