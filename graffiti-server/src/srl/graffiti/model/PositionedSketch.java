package srl.graffiti.model;

import srl.core.sketch.Sketch;

import com.google.appengine.api.datastore.Text;

public class PositionedSketch {
	
	
	public static enum Permission{Public,Private};
	
	private String id;
	private Sketch sketch;
	private double latitude, longitude, altitude;
	private double orientationX, orientationY, orientationZ;
	private double heightX, heightY, heightZ;
	
	private String owner;
	private Permission permissions;
	private Text unstructuredData;
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	/**
	 * @return the orientationX
	 */
	public double getOrientationX() {
		return orientationX;
	}
	/**
	 * @param orientationX the orientationX to set
	 */
	public void setOrientationX(double orientationX) {
		this.orientationX = orientationX;
	}
	/**
	 * @return the orientationY
	 */
	public double getOrientationY() {
		return orientationY;
	}
	/**
	 * @param orientationY the orientationY to set
	 */
	public void setOrientationY(double orientationY) {
		this.orientationY = orientationY;
	}
	/**
	 * @return the orientationZ
	 */
	public double getOrientationZ() {
		return orientationZ;
	}
	/**
	 * @param orientationZ the orientationZ to set
	 */
	public void setOrientationZ(double orientationZ) {
		this.orientationZ = orientationZ;
	}
	/**
	 * @return the heightX
	 */
	public double getHeightX() {
		return heightX;
	}
	/**
	 * @param heightX the heightX to set
	 */
	public void setHeightX(double heightX) {
		this.heightX = heightX;
	}
	/**
	 * @return the heightY
	 */
	public double getHeightY() {
		return heightY;
	}
	/**
	 * @param heightY the heightY to set
	 */
	public void setHeightY(double heightY) {
		this.heightY = heightY;
	}
	/**
	 * @return the heightZ
	 */
	public double getHeightZ() {
		return heightZ;
	}
	/**
	 * @param heightZ the heightZ to set
	 */
	public void setHeightZ(double heightZ) {
		this.heightZ = heightZ;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the permissions
	 */
	public Permission getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Permission permissions) {
		this.permissions = permissions;
	}
	/**
	 * @return the unstructuredData
	 */
	public Text getUnstructuredData() {
		return unstructuredData;
	}
	/**
	 * @param unstructuredData the unstructuredData to set
	 */
	public void setUnstructuredData(Text unstructuredData) {
		this.unstructuredData = unstructuredData;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the sketch
	 */
	public Sketch getSketch() {
		return sketch;
	}
	/**
	 * @param sketch the sketch to set
	 */
	public void setSketch(Sketch sketch) {
		this.sketch = sketch;
	}
}
