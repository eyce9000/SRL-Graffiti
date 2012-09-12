package srl.graffiti.messages;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.grl.json.ServiceContext;
import com.grl.json.messages.ErrorResponse;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;
public class ImageGetSessionRequest extends AuthorizedRequest{
	@Override
	public Response performAuthorizedService(ServiceContext ctx, User user) {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		return new ImageGetSessionResponse(blobstoreService.createUploadUrl("/admin/uploads"));
	}
	
}
