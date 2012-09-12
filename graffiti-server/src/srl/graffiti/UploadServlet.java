package srl.graffiti;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import srl.graffiti.messages.ImageCreatedResponse;
import srl.graffiti.model.Image;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class UploadServlet extends HttpServlet{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory(); 
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		BlobKey blobKey = blobs.get("newImage").get(0);

		ObjectMapper mapper = new ObjectMapper();
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		
		BlobInfo info = blobInfoFactory.loadBlobInfo(blobKey);
		
		String filename = info.getFilename();
		long size = info.getSize();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Image image = new Image();
		image.setImageURL(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
		image.setImageKey(blobKey);
		image.setSize(size);
		image.setFilename(filename);
		pm.makePersistent(image);
		pm.flush();
		pm.close();

		mapper.writeValue(res.getOutputStream(), new ImageCreatedResponse(image.getImageURL()));
	}

}
