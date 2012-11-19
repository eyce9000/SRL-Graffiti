package srl.graffiti.messages.sketch;

import java.util.Collection;

import srl.graffiti.model.PositionedSketch;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;
import srl.distributed.server.ServiceContext;

public class GetMyPositionedSketchesResponse extends Response {
	private Collection<PositionedSketch> positionedSketches;
	
	private GetMyPositionedSketchesResponse(){};
	
	public GetMyPositionedSketchesResponse(Collection<PositionedSketch> sketches){
		this.positionedSketches = sketches;
	}

	/**
	 * @return the positionedSketches
	 */
	public Collection<PositionedSketch> getPositionedSketches() {
		return positionedSketches;
	}

	/**
	 * @param positionedSketches the positionedSketches to set
	 */
	public void setPositionedSketches(
			Collection<PositionedSketch> positionedSketches) {
		this.positionedSketches = positionedSketches;
	}
	
}
