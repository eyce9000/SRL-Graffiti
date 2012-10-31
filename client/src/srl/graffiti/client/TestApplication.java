package srl.graffiti.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.openawt.Color;
import org.openawt.svg.Style;

import srl.core.serialization.TypeAttributeMixin;
import srl.core.sketch.Point;
import srl.core.sketch.Sketch;
import srl.core.sketch.Stroke;
import srl.graffiti.GraffitiSerialization;
import srl.graffiti.messages.images.ImageCreatedResponse;
import srl.graffiti.messages.images.ImageGetSessionRequest;
import srl.graffiti.messages.images.ImageGetSessionResponse;
import srl.graffiti.messages.sketch.SavePositionedSketchRequest;
import srl.graffiti.messages.sketch.SavePositionedSketchResponse;
import srl.graffiti.model.PositionedSketch;
import srl.graffiti.model.PositionedSketch.Permission;


import com.grl.json.JSONMapperProvider;
import com.grl.json.client.Client;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;

public class TestApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Client client;
		String token;
		boolean test = true;
		if (test) {
			client = new Client(new URL("http://localhost:8888/graffiti?key=srl"));
			client.setMessageLoggingEnabled(new File("test.log.json"));
			client.setJSONMapperProvider(GraffitiSerialization.mapperProvider);
			token = LoginHelper.loginToLocalhost(client.getHttpClient(),
					"test@gmail.com", false, "http://localhost:8888/");
			
			

		} else {
			client = new Client(new URL(
					"https://srl-graffiti.appspot.com/graffiti"));

			token = LoginHelper.loginToGoogleAppEngine(client.getHttpClient(),
					"username@gmail.com", "google_password",
					"https://srl-graffiti.appspot.com/graffiti");

			
		}

		if (!client.testConnection()) {
			return;
		}


		testUpload(client);

	}

	public static void testUpload(Client client) {
		String sessionURL = client.sendRequest(new ImageGetSessionRequest(),
				ImageGetSessionResponse.class).getSessionURL();

		UploadProgressListener listener = new UploadProgressListener() {

			@Override
			public void onUploadProgress(File file, long uploadedBytes,
					long totalBytes) {
				double completed = ((double) uploadedBytes)
						/ ((double) totalBytes);
				System.out.println(String.format("%s %d", file.getName(),
						uploadedBytes));
			}

		};

		Response response = ImageUploader.uploadFiles(sessionURL, listener,client,
				new File("image.jpg"));
		String imageURL = ((ImageCreatedResponse) response).getImageURL();
		
		Sketch sketch = new Sketch();
		Stroke stroke = new Stroke();
		stroke.addPoint(new Point(0,0));
		stroke.addPoint(new Point(200,0));
		stroke.addPoint(new Point(100,100));
		sketch.add(stroke);
		
		
		PositionedSketch posSketch = new PositionedSketch();
		posSketch.point = new Point(300,300);
		posSketch.setSketch(sketch);
		posSketch.setImageURL(imageURL);
		posSketch.setLatitude(90);
		posSketch.setLongitude(90);
		posSketch.setPermissions(Permission.Private);
		posSketch.setUnstructuredData("Hello, this is the unstructured data");
		
		SavePositionedSketchResponse saveResponse = client.sendRequest(new SavePositionedSketchRequest(posSketch),SavePositionedSketchResponse.class);
		posSketch = saveResponse.getPositionedSketch();
		System.out.println("Pos Sketch Id: "+posSketch.getId());
	}

}
