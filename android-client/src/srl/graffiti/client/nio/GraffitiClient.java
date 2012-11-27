package srl.graffiti.client.nio;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import srl.distributed.client.Client;
import srl.distributed.messages.Request;
import srl.distributed.messages.Response;
import srl.graffiti.GraffitiSerialization;
import srl.graffiti.client.ImageUploader;
import srl.graffiti.client.LoginHelper;
import srl.graffiti.client.UploadProgressListener;
import srl.graffiti.messages.images.GetMyImagesRequest;
import srl.graffiti.messages.images.GetMyImagesResponse;
import srl.graffiti.messages.images.ImageCreatedResponse;
import srl.graffiti.messages.images.ImageGetSessionRequest;
import srl.graffiti.messages.images.ImageGetSessionResponse;
import srl.graffiti.messages.sketch.GetMyPositionedSketchesRequest;
import srl.graffiti.messages.sketch.GetMyPositionedSketchesResponse;
import srl.graffiti.messages.sketch.GetPublicPositionedSketchesRequest;
import srl.graffiti.messages.sketch.GetPublicPositionedSketchesResponse;
import srl.graffiti.messages.sketch.SavePositionedSketchRequest;
import srl.graffiti.messages.sketch.SavePositionedSketchResponse;
import srl.graffiti.model.Image;
import srl.graffiti.model.PositionedSketch;

import android.os.AsyncTask;
import android.util.Log;

public class GraffitiClient {
	private Client client;
	private URL serverURL;
	private boolean isTestServer;

	/**
	 * Creates a new GraffitiClient.
	 * 
	 * @param serverURL
	 *            the URL for the servlet servering json requests.<br/>
	 *            e.g. https://srl-graffiti.appspot.com/graffiti
	 * @param isTestServer
	 *            indicates whether this connection is to the live server, or to
	 *            a google app engine development environment.
	 */
	public GraffitiClient(URL serverURL, boolean isTestServer) {
		client = new Client(serverURL);
		client.setObjectMapperProvider(new GraffitiSerialization());
		this.serverURL = serverURL;
		this.isTestServer = isTestServer;
	}

	/**
	 * This method must be called first. If the callback does not return
	 * <code>true</code> then the GraffitiClient is not logged in and all calls
	 * to other methods will fail, until logIn completes successfully.
	 * 
	 * @param username
	 *            Your Google account username
	 * @param password
	 *            Your Google account password
	 * @param completionCallback
	 *            A Callback that takes a boolean value. This value indicates
	 *            whether you were able to successfully log in.
	 */
	public void logIn(final String username, final String password,
			final Callback<Boolean> completionCallback) {
		new AsyncTask<Object, Object, Boolean>() {
			@Override
			protected Boolean doInBackground(Object... params) {
				String token;
				try {
					if (!isTestServer) {
						token = LoginHelper.loginToGoogleAppEngine(
								client.getHttpClient(), username, password,
								serverURL.toExternalForm());
					} else {
						token = LoginHelper.loginToDevServer(
								client.getHttpClient(),
								GraffitiClient.this.serverURL.getHost(),
								GraffitiClient.this.serverURL.getPort(),
								username, false, serverURL.toExternalForm());
					}
					if (token != null) {
						return true;
					}
				} catch (Exception e) {
					Log.e("GraffitiClient", "Unable to log in to server.", e);
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean success) {
				completionCallback.onCallback(success);
			}
		}.execute(null);
	}

	/**
	 * Uploads the specified image file to the server. On completion, calls the
	 * provided callback, passing in the URL of the image.
	 * 
	 * @param imageFile
	 *            the image file to be uploaded. JPEG and PNG formats preferred.
	 * @param completionCallback
	 *            this callback is called on completion, and the string passed
	 *            contains the URL where the image can be found.
	 */
	public void uploadImage(final File imageFile,
			final Callback<String> completionCallback) {
		new AsyncTask<File, Double, String>() {
			@Override
			protected String doInBackground(File... params) {
				Log.i("GraffitiClientTest", "Uploading file "+params[0].getPath());
				try {
					
					Response response = client.sendRequest(new ImageGetSessionRequest(),30000);
					
					if (response instanceof ImageGetSessionResponse) {
						String sessionURL = ((ImageGetSessionResponse) response)
								.getSessionURL();
						
						UploadProgressListener listener = new UploadProgressListener(){

							public void onUploadProgress(File file,
									long uploadedBytes, long totalBytes) {
								Log.i("GraffitiClientTest","Uploading file. Completion "+uploadedBytes+"/"+totalBytes);
							}
							
						};
						
						response = ImageUploader.uploadFiles(sessionURL, listener ,params[0]);
						if (response instanceof ImageCreatedResponse) {
							return ((ImageCreatedResponse) response)
									.getImageURL();
						} else {
							Log.e("GraffitiClientTest",
									"Unable to upload image");
						}
					} else {
						Log.e("GraffitiClientTest",
								"Unable to start Image Session");
					}
				} catch (Exception ex) {
					Log.e("GraffitiClientTest",
							"Error occurred while uploading image", ex);
				}
				return null;
			}

			@Override
			protected void onPostExecute(String imageURL) {
				completionCallback.onCallback(imageURL);
			}
		}.execute(imageFile);
	}

	/**
	 * Stores the positioned sketch on the server. To save over an existing
	 * positioned sketch, provide a positioned sketch with the same ID as one
	 * already stored.
	 * 
	 * @param posSketch
	 *            The positioned sketch to store. Note that if the positioned
	 *            sketch already has an id, it will not be saved.
	 * @param completionCallback
	 *            The callback called on completion. The callback is passed the
	 *            saved positioned sketch, including the new id and owner name.
	 */
	public void storePositionedSketch(final PositionedSketch posSketch,
			final Callback<PositionedSketch> completionCallback) {
		AsyncTask task = new AsyncTask<PositionedSketch, Object, PositionedSketch>() {
			@Override
			protected PositionedSketch doInBackground(
					PositionedSketch... params) {
				SavePositionedSketchResponse saveResponse = client.sendRequest(
						new SavePositionedSketchRequest(posSketch),
						SavePositionedSketchResponse.class);
				return saveResponse.getPositionedSketch();
			}

			@Override
			protected void onPostExecute(PositionedSketch positionedSketch) {
				completionCallback.onCallback(positionedSketch);
			}
		};
		task.execute(posSketch);
	}

	/**
	 * Gets all images uploaded by the currently logged in user.
	 * 
	 * @param completionCallback
	 *            the callback called on completion. The callback is passed a
	 *            collection of images belonging to the currently logged in
	 *            user.
	 */
	public void getMyImages(final Callback<Collection<Image>> completionCallback) {
		AsyncTask task = new AsyncTask<Object, Object, Collection<Image>>() {
			@Override
			protected Collection<Image> doInBackground(Object... params) {
				GetMyImagesResponse imagesResponse = client.sendRequest(
						new GetMyImagesRequest(), GetMyImagesResponse.class);
				return imagesResponse.getImages();
			}

			@Override
			protected void onPostExecute(Collection<Image> images) {
				completionCallback.onCallback(images);
			}
		};
		task.execute(null);
	}

	/**
	 * Gets all positioned sketches created by the currently logged in user.
	 * 
	 * @param completionCallback
	 *            the callback called on completion. The callback is passed a
	 *            collection of positioned sketches belonging to the currently
	 *            logged in user.
	 */
	public void getMyPositionedSketches(
			final Callback<Collection<PositionedSketch>> completionCallback) {
		AsyncTask task = new AsyncTask<Object, Object, Collection<PositionedSketch>>() {
			@Override
			protected Collection<PositionedSketch> doInBackground(
					Object... params) {
				GetMyPositionedSketchesResponse sketchesResponse = client
						.sendRequest(new GetMyPositionedSketchesRequest(),
								GetMyPositionedSketchesResponse.class);
				return sketchesResponse.getPositionedSketches();
			}

			@Override
			protected void onPostExecute(Collection<PositionedSketch> sketches) {
				completionCallback.onCallback(sketches);
			}
		};
		task.execute(null);
	}

	/**
	 * Gets all positioned sketches that have public permissions
	 * 
	 * @param completionCallback
	 *            the callback called on completion. The callback is passed a
	 *            collection of all public positioned sketches.
	 */
	public void getPublicPositionedSketches(
			final Callback<Collection<PositionedSketch>> completionCallback) {
		AsyncTask task = new AsyncTask<Object, Object, Collection<PositionedSketch>>() {
			@Override
			protected Collection<PositionedSketch> doInBackground(
					Object... params) {
				GetPublicPositionedSketchesResponse sketchesResponse = client
						.sendRequest(new GetPublicPositionedSketchesRequest(),
								GetPublicPositionedSketchesResponse.class);
				return sketchesResponse.getPositionedSketches();
			}

			@Override
			protected void onPostExecute(Collection<PositionedSketch> sketches) {
				completionCallback.onCallback(sketches);
			}
		};
		task.execute(null);
	}
}
