package srl.graffiti.client;

/*******************************************************************************
 *  Revision History:<br>
 *  George R. Lucchese - File created
 *
 *  <p>
 *  <pre>
 *  This work is released under the BSD License:
 *  (C) 2012 Sketch Recognition Lab, Texas A&M University (hereafter SRL @ TAMU)
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the Sketch Recognition Lab, Texas A&M University 
 *        nor the names of its contributors may be used to endorse or promote 
 *        products derived from this software without specific prior written 
 *        permission.
 *  
 *  THIS SOFTWARE IS PROVIDED BY SRL @ TAMU ``AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL SRL @ TAMU BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  </pre>
 *  
 *******************************************************************************/

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
