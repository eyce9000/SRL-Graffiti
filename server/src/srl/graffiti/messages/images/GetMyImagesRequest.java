package srl.graffiti.messages.images;

import com.google.appengine.api.users.User;
import com.grl.json.ServiceContext;
import com.grl.json.messages.Response;

import srl.graffiti.managers.ImageManager;
import srl.graffiti.messages.AuthorizedRequest;

public class GetMyImagesRequest extends AuthorizedRequest {

	@Override
	public Response performAuthorizedService(ServiceContext ctx, User user) {
		return new GetMyImagesResponse(ImageManager.getMyImages(user));
	}

}
