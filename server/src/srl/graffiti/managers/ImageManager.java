package srl.graffiti.managers;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;

import srl.graffiti.PMF;
import srl.graffiti.model.Image;

public class ImageManager {
	public static Image createImage(BlobKey blobKey, User user){
		ImagesService imagesService = ImagesServiceFactory
				.getImagesService();

		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		BlobInfo info = blobInfoFactory.loadBlobInfo(blobKey);

		String filename = info.getFilename();
		long size = info.getSize();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Image image = new Image();
		image.setImageURL(imagesService
				.getServingUrl(ServingUrlOptions.Builder
						.withBlobKey(blobKey)));
		image.setImageKey(blobKey);
		image.setSize(size);
		image.setFilename(filename);
		image.setTimestamp(System.currentTimeMillis());
		image.setOwnerId(user.getUserId());
		image.setOwnerName(user.getNickname());
		
		pm.makePersistent(image);
		pm.flush();
		pm.close();
		return image;
	}
	
	public static Collection<Image> getMyImages(User user){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery(Image.class);
		q.setFilter("ownerId==myUserId");
		q.declareParameters("String myUserId");
		Collection<Image> images = (Collection<Image>)q.execute(user.getUserId());
		images = pm.detachCopyAll(images);
		pm.close();
		return images;

	}
}
