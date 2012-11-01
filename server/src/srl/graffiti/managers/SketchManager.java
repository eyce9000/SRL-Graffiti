package srl.graffiti.managers;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;

import srl.graffiti.PMF;
import srl.graffiti.model.PositionedSketch;
import srl.graffiti.model.UserInfo;

public class SketchManager {
	public static PositionedSketch storePositionedSketch(
			PositionedSketch positionedSketch, User user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if (positionedSketch != null) {
			if (positionedSketch.getId() == null) {
				positionedSketch.setOwnerId(user.getUserId());
				positionedSketch.setOwnerName(user.getNickname());
				positionedSketch.setTimestamp(System.currentTimeMillis());
				pm.makePersistent(positionedSketch);
				pm.close();
			}
		}
		return positionedSketch;
	}
}
