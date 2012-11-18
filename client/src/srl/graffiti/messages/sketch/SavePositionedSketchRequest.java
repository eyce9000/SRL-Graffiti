package srl.graffiti.messages.sketch;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;

import srl.graffiti.model.PositionedSketch;

public class SavePositionedSketchRequest extends Request {

	private PositionedSketch positionedSketch;
	
	public SavePositionedSketchRequest(PositionedSketch positionedSketch){
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
