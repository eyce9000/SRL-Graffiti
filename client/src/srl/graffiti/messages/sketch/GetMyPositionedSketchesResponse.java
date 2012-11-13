package srl.graffiti.messages.sketch;

import java.util.Collection;

import srl.graffiti.model.PositionedSketch;

import com.grl.json.messages.Response;

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
