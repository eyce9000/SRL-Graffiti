package srl.graffiti.messages;

import com.grl.json.messages.Response;

public class LoginResponse extends Response {
	private String loginURL;
	public LoginResponse(String loginURL){
		this.loginURL = loginURL;
	}
}
