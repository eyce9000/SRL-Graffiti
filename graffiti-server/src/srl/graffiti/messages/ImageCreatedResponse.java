package srl.graffiti.messages;

import com.grl.json.messages.Response;

public class ImageCreatedResponse extends Response {
	private String imageURL;
	public ImageCreatedResponse(String imageURL){
		this.imageURL = imageURL;
	}
}
