package srl.graffiti.messages.sketch;

import java.util.Collection;

import srl.graffiti.model.PositionedSketch;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;

public class GetPublicPositionedSketchesResponse extends Response {
	private Collection<PositionedSketch> positionedSketches;
	
	private GetPublicPositionedSketchesResponse(){};
	
	public GetPublicPositionedSketchesResponse(Collection<PositionedSketch> sketches){
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
