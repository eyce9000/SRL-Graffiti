package srl.graffiti.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import srl.distributed.messages.Response;
import srl.distributed.messages.UnauthorizedResponse;
import srl.distributed.server.ServerRequest;
import srl.distributed.server.ServiceContext;

public class BatchRequest extends PrivateRequest {
	private List<ServerRequest> requests;
	
	public BatchRequest(){};
	public BatchRequest(ServerRequest...messages){
		this.requests = Arrays.asList(messages);
	}
	
	public BatchRequest(List<ServerRequest> messages){
		this.requests = messages;
	}
	

	/**
	 * @return the messages
	 */
	public List<ServerRequest> getRequests() {
		return requests;
	}
	/**
	 * @param messages the messages to set
	 */
	public void setRequests(List<ServerRequest> messages) {
		this.requests = messages;
	}

	
	
	@Override
	public Response performPrivateService(ServiceContext context) {
		List<Response> responses = new ArrayList<Response>();
		for(ServerRequest message:requests){
			responses.add(message.performService(context));
		}
		return new BatchResponse(responses);
	}
}
