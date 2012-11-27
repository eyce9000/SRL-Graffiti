package srl.graffiti;

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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import srl.graffiti.managers.ImageManager;
import srl.graffiti.messages.images.ImageCreatedResponse;
import srl.graffiti.model.Image;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UploadServlet extends HttpServlet {
	
    private static final Logger log = Logger.getLogger(UploadServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		log.info("Upload servlet active");
		if (user != null) {
			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
			BlobKey blobKey = null;
			for(String key:blobs.keySet()){
				List<BlobKey> blobKeys = blobs.get(key);
				for(BlobKey foundKey:blobKeys){
					blobKey = foundKey;
					break;
				}
				if(blobKey!=null)
					break;
			}
			log.info("User "+user.getEmail()+" attempting to upload "+blobs.entrySet().size()+" files.");
			ObjectMapper mapper = new GraffitiSerialization().getMapper();
			Image image = null;
			if(blobKey!=null){


				image = ImageManager.createImage(blobKey, user);

				System.out.println("Stored image: " + image.getImageURL()
						+ " uploaded by " + user.getUserId());

				if (GraffitiServlet.isDevelopment) {
					String ip = req.getLocalAddr();
					String imageURL = image.getImageURL();
					imageURL = imageURL.replaceAll("//.*:8888", "//" + ip
							+ ":8888");
					image.setImageURL(imageURL);
				}

			}
			String imageURL = null;
			if(image!=null)
				imageURL = image.getImageURL();
			mapper.writeValue(res.getOutputStream(),
					new ImageCreatedResponse(imageURL));
		}
	}

}
