package srl.graffiti.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import srl.distributed.messages.ErrorResponse;
import srl.distributed.messages.Response;
import srl.graffiti.GraffitiSerialization;


public class ImageUploader {
	private static Logger log = LoggerFactory.getLogger("ImageUploader");
	private static String authCookieName;
	private static String authCookieValue;
	public static void configureAuthentication(String cookieName, String value){
		authCookieName = cookieName;
		authCookieValue = value;
	}
	
	public static Response uploadFiles(String uploadUrl,UploadProgressListener listener
			,File imageFile){
		/*
		 * This upload code is a modified version of that found on this website
		 * http://reecon.wordpress.com/2010/04/25/uploading-files-to-http-server-using-post-android-sdk/
		 */
		
		log.info("Uploading to "+uploadUrl);
		
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;

		String urlServer = uploadUrl;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		try {
			FileInputStream fileInputStream = new FileInputStream(imageFile);

			URL url = new URL(urlServer);
			connection = (HttpURLConnection) url.openConnection();

			// Allow Inputs & Outputs
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// Enable POST method
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			connection.addRequestProperty("Cookie",authCookieName+"="+authCookieValue);
			connection.setConnectTimeout(30000);
			outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream
					.writeBytes("Content-Disposition: form-data; name=\"newImage\";filename=\""
							+ imageFile.getAbsolutePath() + "\"" + lineEnd);
			outputStream.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// Read file
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			long length = imageFile.length();
			long totalSent = bytesRead;
			
			while (bytesRead > 0) {
				outputStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				totalSent += bytesRead;
				if(listener!=null)
					listener.onUploadProgress(imageFile, totalSent, length);
			}

			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens
					+ lineEnd);

			outputStream.flush();
			outputStream.close();
			
			// Responses from the server (code and message)
			int serverResponseCode = connection.getResponseCode();
			log.info("Image upload response: "+serverResponseCode);
			String serverResponseMessage = connection.getResponseMessage();
			Response response = new GraffitiSerialization().getMapper().readValue(
					connection.getInputStream(), Response.class);

			fileInputStream.close();
			outputStream.flush();
			outputStream.close();

			return response;
		} catch (Exception ex) {
			log.error("Upload error", ex);
			return new ErrorResponse(ex);
		}
	}
}
