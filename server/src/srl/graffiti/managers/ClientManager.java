package srl.graffiti.managers;

public class ClientManager {
	public static boolean isClientKeyValid(String clientKey){
		if(clientKey.equals("srl"))
			return true;
		return false;
	}
}
