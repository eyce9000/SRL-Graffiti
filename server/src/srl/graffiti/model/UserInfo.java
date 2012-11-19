package srl.graffiti.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import srl.distributed.messages.SerializableObject;

import com.google.appengine.api.users.User;

@PersistenceCapable
public class UserInfo extends SerializableObject{
	@Persistent
	private String name;
	@PrimaryKey
	@Persistent
	private String userId;
	@Persistent
	private String email;
	
	public UserInfo(User user){
		name = user.getNickname();
		userId = user.getUserId();
		email = user.getEmail();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
