package srl.graffiti.messages.images;

import java.util.Collection;

import srl.graffiti.model.Image;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;

public class GetMyImagesResponse extends Response{
	private Collection<Image> images;
	
	private GetMyImagesResponse(){}
	
	public GetMyImagesResponse(Collection<Image> images){
		this.images = images;
	}

	/**
	 * @return the images
	 */
	public Collection<Image> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(Collection<Image> images) {
		this.images = images;
	}
}
