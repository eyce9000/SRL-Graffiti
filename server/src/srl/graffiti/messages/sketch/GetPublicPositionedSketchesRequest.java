package srl.graffiti.messages.sketch;

import com.google.appengine.api.users.User;
import com.grl.json.ServiceContext;
import com.grl.json.messages.Response;

import srl.graffiti.managers.SketchManager;
import srl.graffiti.messages.AuthorizedRequest;

public class GetPublicPositionedSketchesRequest extends AuthorizedRequest{

	@Override
	public Response performAuthorizedService(ServiceContext ctx, User user) {
		return new GetPublicPositionedSketchesResponse(SketchManager.getPublicSketches());
	}
	
}