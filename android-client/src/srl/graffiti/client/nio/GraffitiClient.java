package srl.graffiti.client.nio;

import java.io.File;
import java.net.URL;
import java.util.List;

import srl.graffiti.client.ImageUploader;
import srl.graffiti.client.LoginHelper;
import srl.graffiti.messages.images.ImageCreatedResponse;
import srl.graffiti.messages.images.ImageGetSessionRequest;
import srl.graffiti.messages.images.ImageGetSessionResponse;
import srl.graffiti.messages.sketch.SavePositionedSketchRequest;
import srl.graffiti.messages.sketch.SavePositionedSketchResponse;
import srl.graffiti.model.PositionedSketch;

import android.os.AsyncTask;
import android.util.Log;

import com.grl.json.client.Client;
import com.grl.json.client.exceptions.ClientException;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;

public class GraffitiClient {
	private Client client;
	private URL serverURL;
	private boolean isTestServer;
	
	/**
	 * Creates a new GraffitiClient.
	 * @param serverURL the URL for the servlet servering json requests.<br/>
	 * e.g. https://srl-graffiti.appspot.com/graffiti
	 * @param isTestServer indicates whether this connection is to the live server, or to a google app engine development environment.
	 */
	public GraffitiClient(URL serverURL, boolean isTestServer){
		client = new Client(serverURL);
		this.serverURL = serverURL;
		this.isTestServer = isTestServer;
	}
	
	private void sendRequests(Callback<List<Response>> successCallback, Request... requests){
		RequestTask task = new RequestTask(client,successCallback);
		task.execute(requests);
	}
	
	/**
	 * This method must be called first. 
	 * If the callback does not return <code>true</code> then the GraffitiClient is not logged in 
	 *  and all calls to other methods will fail, until logIn completes successfully.
	 * @param username Your Google account username
	 * @param password Your Google account password
	 * @param completionCallback A Callback that takes a boolean value. This value indicates whether you were able to successfully log in.
	 */
	public void logIn(final String username, final String password, final Callback<Boolean> completionCallback){
		new AsyncTask<Object,Object,Boolean>(){
			@Override
			protected Boolean doInBackground(Object... params) {
				String token;
				try {
					if(!isTestServer){
						token = LoginHelper.loginToGoogleAppEngine(client.getHttpClient(), username, password, serverURL.toExternalForm());
					}
					else{
						token = LoginHelper.loginToLocalhost(client.getHttpClient(), username, false, serverURL.toExternalForm());
					}
					if(token!=null){
						return true;
					}
				} catch (Exception e) {
					Log.e("GraffitiClient", "Unable to log in to server.", e);
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean success){
				completionCallback.onCallback(success);
			}
		}.equals(null);
	}
	
	/**
	 * Uploads the specified image file to the server. 
	 * On completion, calls the provided callback, passing in the URL of the image.
	 * @param imageFile the image file to be uploaded. JPEG and PNG formats preferred.
	 * @param completionCallback this callback is called on completion, 
	 *  and the string passed contains the URL where the image can be found.
	 */
	public void uploadImage(final File imageFile, final Callback<String> completionCallback){
		AsyncTask task = new AsyncTask<File,Double,String>(){
			@Override
			protected String doInBackground(File... params) {
				String sessionURL = client.sendRequest(new ImageGetSessionRequest(),ImageGetSessionResponse.class).getSessionURL();
				Response response = ImageUploader.uploadFiles(sessionURL, null, imageFile);
				if(response instanceof ImageCreatedResponse){
					return ((ImageCreatedResponse) response).getImageURL();
				}
				else {
					return null;
				}
			}
			

			@Override
			protected void onPostExecute(String imageURL){
				completionCallback.onCallback(imageURL);
			}
		};
		task.execute(imageFile);
	}
	
	/**
	 * Stores the positioned sketch on the server. Note that currently, once saved, a positioned sketch is immutable. This may change in future versions.
	 * @param posSketch The positioned sketch to store. Note that if the positioned sketch already has an id, it will not be saved.
	 * @param completionCallback The callback called on completion. The callback is passed the saved positioned sketch, including the new id and owner name.
	 */
	public void storePositionedSketch(final PositionedSketch posSketch, final Callback<PositionedSketch> completionCallback){
		AsyncTask task = new AsyncTask<PositionedSketch,Object,PositionedSketch>(){
			@Override
			protected PositionedSketch doInBackground(PositionedSketch... params) {
				SavePositionedSketchResponse saveResponse = client.sendRequest(new SavePositionedSketchRequest(posSketch),SavePositionedSketchResponse.class);
				return saveResponse.getPositionedSketch();
			}
			

			@Override
			protected void onPostExecute(PositionedSketch positionedSketch){
				completionCallback.onCallback(positionedSketch);
			}
		};
		task.execute(posSketch);
	}
}
