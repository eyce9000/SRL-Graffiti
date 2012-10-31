package srl.graffiti.managers;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;

import srl.graffiti.PMF;
import srl.graffiti.model.Image;

public class ImageManager {
	public static Collection<Image> getMyImages(User user){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Image.class);
		q.setFilter("userId=myUserId");
		q.declareParameters("String myUserId");
		List<Image> images = (List<Image>)q.execute(user.getUserId());
		pm.close();
		return pm.detachCopyAll(images);
	}
}
