package srl.graffiti.messages.sketch;

import srl.graffiti.model.PositionedSketch;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;

public class SavePositionedSketchResponse extends Response {
	private PositionedSketch positionedSketch;
	
	private SavePositionedSketchResponse(){};
	public SavePositionedSketchResponse(PositionedSketch positionedSketch){
		this.positionedSketch = positionedSketch;
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
