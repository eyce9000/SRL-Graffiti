package srl.graffiti.managers;

import javax.jdo.PersistenceManager;

import srl.graffiti.PMF;
import srl.graffiti.model.UserInfo;

import com.google.appengine.api.users.User;

public class UserManager {
	public static void storeUser(User user){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(new UserInfo(user));
		pm.close();
	}
}
