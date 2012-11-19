package srl.graffiti.messages.sketch;

import com.google.appengine.api.users.User;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;
import srl.distributed.server.ServiceContext;

import srl.graffiti.managers.SketchManager;
import srl.graffiti.messages.AuthorizedRequest;

public class GetPublicPositionedSketchesRequest extends AuthorizedRequest{

	@Override
	public Response performAuthorizedService(ServiceContext ctx, User user) {
		return new GetPublicPositionedSketchesResponse(SketchManager.getPublicSketches());
	}
	
}
