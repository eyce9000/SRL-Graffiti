package srl.graffiti.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * Google login helper.
 * 
 * Based on work of Cheers Geoff and Alois Belaska
 * 
 * http://groups.google.com/group/google-appengine
 * -java/browse_thread/thread/c96d4fff73117e1d?pli=1
 * 
 * http://stackoverflow.com/questions/6357586/
 * how-to-authenticate-programmatically-to-google-app-engine-with-java
 * 
 * @author George Lucchese
 */
public class LoginHelper {

	/**
	 * Returns the ACSID string to be set as the Cookie field in the request
	 * header.
	 * 
	 * @param userid
	 *            full gmail address for user
	 * @param password
	 *            password
	 * @param source
	 *            name of application requesting quota details
	 * @return the ACSID field value
	 * @throws Exception
	 *             if any error occurs getting the ACSID
	 */
	public static String loginToGoogleAppEngine(DefaultHttpClient client,
			String userid, String password, String source) throws Exception {

		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accountType", "HOSTED_OR_GOOGLE"));
			nvps.add(new BasicNameValuePair("Email", userid));
			nvps.add(new BasicNameValuePair("Passwd", password));
			nvps.add(new BasicNameValuePair("service", "ah"));
			nvps.add(new BasicNameValuePair("source", source));

			HttpPost post = new HttpPost(
					"https://www.google.com/accounts/ClientLogin");
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() != 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line;
				while((line=reader.readLine())!=null){
					System.out.println(line);
				}
				throw new Exception("Error obtaining SACSID");
			}

			String authToken = getAuthToken(response.getEntity().getContent());

			post.abort();

			
			HttpGet get = new HttpGet(
					"https://srl-graffiti.appspot.com/_ah/login?auth="
							+ authToken);

			response = client.execute(get);

			List<Cookie> cookies = client.getCookieStore().getCookies();

			for (Cookie cookie : cookies) {
				System.out.println(cookie.getDomain() + " " + cookie.getName()
						+ " " + cookie.getValue());
			}
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("SACSID")) {
					ImageUploader.configureAuthentication(cookie.getName(), cookie.getValue());
					return cookie.getValue();
				}
			}

			get.abort();
			throw new Exception("Did not find SACSID cookie");

		} finally {
			// client.getConnectionManager().shutdown();
		}
	}

	public static String consumeContent(InputStream inputStream)
			throws Exception {
		LineNumberReader reader = new LineNumberReader(new BufferedReader(
				new InputStreamReader(inputStream)));
		String content = "";
		String line;
		while ((line = reader.readLine()) != null) {
			content += line;
		}
		return content;
	}

	private static String getAuthToken(InputStream inputStream)
			throws Exception {
		LineNumberReader reader = new LineNumberReader(new BufferedReader(
				new InputStreamReader(inputStream)));
		String line = reader.readLine();
		while (line != null) {
			line = line.trim();
			if (line.startsWith("Auth=")) {
				return line.substring(5);
			}
			line = reader.readLine();
		}
		throw new Exception("Could not find Auth token");
	}

	/*
	 * FROM
	 * http://e-mentality.blogspot.com/2011/12/app-engine-sdk-httpclient.html
	 */

	public static String loginToDevServer(DefaultHttpClient client,String hostname,int port,
			String username, boolean asAdmin, String redirectUrl)
			throws Exception {
		client.getParams().setBooleanParameter(
				ClientPNames.HANDLE_REDIRECTS, false);

		String url="http://"+hostname+":"+port+"/_ah/login";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		String email = URLEncoder.encode(username, "UTF-8");
		httpPost.setEntity(new StringEntity("email=" + email + "&continue="
				+ redirectUrl + (asAdmin ? "&isAdmin=on" : "")));
		HttpResponse response = client.execute(httpPost);

		String authenticationCookie = null;
//		response.getFirstHeader("Set-Cookie")
//				.getValue();
//		response.getEntity().consumeContent();
		httpPost.abort();
		List<Cookie> cookies = client.getCookieStore().getCookies();

		for (Cookie cookie : cookies) {
			System.out.println(cookie.getDomain() + " " + cookie.getName()
					+ " " + cookie.getValue());
			if(cookie.getName().equals("dev_appserver_login")){
				ImageUploader.configureAuthentication(cookie.getName(), cookie.getValue());
				authenticationCookie= cookie.getValue();
			}
		}
		return authenticationCookie;
	}
}
