package srl.graffiti.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.grl.json.SerializableObject;

@PersistenceCapable(detachable="true")
public class Image extends SerializableObject{

	@Persistent
	private BlobKey imageKey;

	@PrimaryKey
	@Persistent
	private String imageURL;
	
	@Persistent
	private String filename;
	
	@Persistent
	private long size;
	
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	public Image(){}

	/**
	 * @return the imageKey
	 */
	public BlobKey getImageKey() {
		return imageKey;
	}

	/**
	 * @param imageKey the imageKey to set
	 */
	public void setImageKey(BlobKey imageKey) {
		this.imageKey = imageKey;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	};
	
	
}
