package srl.graffiti.client;

import java.io.File;

public interface UploadProgressListener {
	public void onUploadProgress(File file, long uploadedBytes, long totalBytes);
}
