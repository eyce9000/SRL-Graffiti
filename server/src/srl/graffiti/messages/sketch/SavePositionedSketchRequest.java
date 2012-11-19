package srl.graffiti.messages.sketch;

import com.google.appengine.api.users.User;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;
import srl.distributed.server.ServiceContext;

import srl.graffiti.managers.SketchManager;
import srl.graffiti.messages.AuthorizedRequest;
import srl.graffiti.model.PositionedSketch;

public class SavePositionedSketchRequest extends AuthorizedRequest {

	private PositionedSketch positionedSketch;
	private SavePositionedSketchRequest(){};
	public SavePositionedSketchRequest(PositionedSketch positionedSketch){
		this.positionedSketch = positionedSketch;
	}
	
	@Override
	public Response performAuthorizedService(ServiceContext ctx, User user) {
		return new SavePositionedSketchResponse(SketchManager.storePositionedSketch(positionedSketch, user));
	}
	/**
	 * @return the positionedSketch
	 */
	public PositionedSketch getPositionedSketch() {
		return positionedSketch;
	}
	/**
	 * @param positionedSketch the positionedSketch to set
	 */
	public void setPositionedSketch(PositionedSketch positionedSketch) {
		this.positionedSketch = positionedSketch;
	}

}
