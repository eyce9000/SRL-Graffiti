package srl.graffiti.messages;

import javax.servlet.http.HttpSession;

import com.grl.json.ServiceContext;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;
import com.grl.json.messages.UnauthorizedResponse;
import com.grl.json.server.ServerRequest;

public abstract class PrivateRequest extends ServerRequest {
	private static final String KEY = "srl";
	
	@Override
	public final Response performService(ServiceContext context) {
		String key = (String)context.getRequest().getAttribute("key");
		if(key!=null && key.equals(KEY)){
			return performPrivateService(context);
		}
		return new UnauthorizedResponse();
	}
	
	public abstract Response performPrivateService(ServiceContext context);

}
