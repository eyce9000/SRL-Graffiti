package srl.graffiti.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import srl.distributed.messages.Request;
import srl.distributed.messages.Response;

public class BatchResponse extends Response{

	private List<Response> responses = new ArrayList<Response>();
	
	public BatchResponse(){};
	
	/**
	 * @return the messages
	 */
	public List<Response> getResponses() {
		return responses;
	}

}
