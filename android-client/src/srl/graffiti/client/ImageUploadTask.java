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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.grl.json.JSONMapperProvider;
import com.grl.json.messages.ErrorResponse;
import com.grl.json.messages.Response;

import android.os.AsyncTask;
import android.util.Log;

public class ImageUploadTask extends AsyncTask<File, Double, Response> {
	private String uploadUrl;
	private JSONMapperProvider mapperProvider;
	private UploadProgressListener listener;
	public ImageUploadTask(String uploadUrl, JSONMapperProvider mapperProvider){
		this.uploadUrl = uploadUrl;
		this.mapperProvider = mapperProvider;
		listener = new UploadProgressListener(){

			@Override
			public void onUploadProgress(File file, long uploadedBytes,
					long totalBytes) {
				ImageUploadTask.this.onProgressUpdate(((double)uploadedBytes)/((double)totalBytes));
			}
			
		};
	}
	
	@Override
	protected Response doInBackground(File... imageFiles) {
		
		return ImageUploader.uploadFiles(uploadUrl, listener, mapperProvider, imageFiles[0]);
	}

}
