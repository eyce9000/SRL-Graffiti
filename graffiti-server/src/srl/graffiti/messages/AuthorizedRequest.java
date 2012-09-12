package srl.graffiti.messages;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.grl.json.ServiceContext;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;

public abstract class AuthorizedRequest extends PrivateRequest {

	@Override
	public final Response performPrivateService(ServiceContext ctx) {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if(user != null){
        	return performAuthorizedService(ctx,user);
        }
        else{
        	return new LoginRequest().performService(ctx);
        }
	}
	
	public abstract Response performAuthorizedService(ServiceContext ctx, User user);

}
