package srl.graffiti.managers;

import java.util.ArrayList;
import java.util.Collection;

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
			positionedSketch.setOwnerId(user.getUserId());
			positionedSketch.setOwnerName(user.getNickname());
			positionedSketch.setTimestamp(System.currentTimeMillis());
			if (positionedSketch.getId() == null) {
				pm.makePersistent(positionedSketch);
				pm.close();
			}
			else{
				PositionedSketch storedSketch = pm.getObjectById(PositionedSketch.class, positionedSketch.getId());
				if(storedSketch.getOwnerId().equals(positionedSketch.getOwnerId())){
					pm.makePersistent(positionedSketch);
				}
			}
		}
		return positionedSketch;
	}
	
	public static Collection<PositionedSketch> getMySketches(User user){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(PositionedSketch.class);
		query.declareParameters("String ownerIdParam");
		query.setFilter("ownerId==ownerIdParam");
		Collection<PositionedSketch> sketches = (Collection<PositionedSketch>)query.execute(user.getUserId());
		sketches = pm.detachCopyAll(sketches);
		if(sketches==null)
			sketches = new ArrayList<PositionedSketch>();
		return sketches;
	}
	
	public static Collection<PositionedSketch> getPublicSketches(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(PositionedSketch.class);
		query.setFilter("permissions=='Public'");
		Collection<PositionedSketch> sketches = (Collection<PositionedSketch>)query.execute();
		sketches = pm.detachCopyAll(sketches);
		if(sketches==null)
			sketches = new ArrayList<PositionedSketch>();
		return sketches;
	}
}
