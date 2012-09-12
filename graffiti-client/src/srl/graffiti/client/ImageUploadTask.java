package srl.graffiti.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.grl.json.JSONSerialization;
import com.grl.json.messages.ErrorResponse;
import com.grl.json.messages.Response;

import android.os.AsyncTask;
import android.util.Log;

public class ImageUploadTask extends AsyncTask<File, Double, Response> {
	private String uploadURL;
	public ImageUploadTask(String uploadURL){
		this.uploadURL = uploadURL;
	}
	
	@Override
	protected Response doInBackground(File... imageFiles) {
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;

		String urlServer = uploadURL;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File imageFile = imageFiles[0];
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

			double length = imageFile.length();
			double totalSent = (double)bytesRead;
			
			while (bytesRead > 0) {
				outputStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				totalSent += bytesRead;
				this.onProgressUpdate(totalSent/length);
			}

			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens
					+ lineEnd);

			// Responses from the server (code and message)
			int serverResponseCode = connection.getResponseCode();
			String serverResponseMessage = connection.getResponseMessage();

			Response response = JSONSerialization.getMapper().readValue(
					serverResponseMessage, Response.class);

			fileInputStream.close();
			outputStream.flush();
			outputStream.close();

			return response;
		} catch (Exception ex) {
			Log.e("ImageUploadTask", "Upload error", ex);
			return new ErrorResponse(ex);
		}
	}

}
