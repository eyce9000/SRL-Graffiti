package srl.graffiti.messages;
import com.grl.json.messages.ErrorResponse;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;
public class ImageGetSessionResponse extends Response {
	private String sessionURL;
	
	public ImageGetSessionResponse(){
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionURL() {
		return sessionURL;
	}
}
