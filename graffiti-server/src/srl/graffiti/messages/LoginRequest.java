package srl.graffiti.messages;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.grl.json.ServiceContext;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;

public class LoginRequest extends PrivateRequest {

	@Override
	public Response performPrivateService(ServiceContext ctx) {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        return new LoginResponse(userService.createLoginURL(""));
	}

}
