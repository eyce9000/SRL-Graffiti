package srl.graffiti.messages;
import com.grl.json.messages.ErrorResponse;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;
public class ImageGetSessionResponse extends Response {
	private String sessionURL;
	public ImageGetSessionResponse(String sessionURL){
		this.sessionURL = sessionURL;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionURL() {
		return sessionURL;
	}
	/**
	 * @param sessionURL the sessionId to set
	 */
	public void setSessionURL(String sessionURL) {
		this.sessionURL = sessionURL;
	}
}
