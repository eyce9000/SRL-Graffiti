package srl.graffiti.client;

import java.net.MalformedURLException;
import java.net.URL;

import srl.graffiti.messages.ImageGetSessionRequest;
import srl.graffiti.messages.ImageGetSessionResponse;
import srl.graffiti.messages.LoginRequest;
import srl.graffiti.messages.LoginResponse;

import android.util.Log;

import com.grl.json.client.Client;

public class GraffitiClient {
	
	public static final String SERVER_URL = "http://localhost:8888/";
	public static final String REQUESTS_URL = SERVER_URL+"requests";
	public static final String CLIENT_TOKEN = "srl";
	
	private Client client;
	
	public GraffitiClient(){
		try {
			client = new Client(new URL(REQUESTS_URL+"/?key="+CLIENT_TOKEN));
		} catch (MalformedURLException e) {
			Log.e("GraffitiClient", "Bad Connection URL", e);
		}
	}
	
	public boolean testConnection(){
		return client.testConnection();
	}
	
	public String getLoginURL(){
		LoginResponse resp = client.sendRequest(new LoginRequest(),LoginResponse.class);
		return resp.getLoginURL();
	}
	
	public String getUploadURL(){
		ImageGetSessionResponse resp = client.sendRequest( new ImageGetSessionRequest(),ImageGetSessionResponse.class);
		return resp.getSessionURL();
	}
}
