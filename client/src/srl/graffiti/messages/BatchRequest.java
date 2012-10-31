package srl.graffiti.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.grl.json.ServiceContext;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;

public class BatchRequest extends Request {
	private List<Request> requests;
	
	public BatchRequest(){};
	public BatchRequest(Request...messages){
		this.requests = Arrays.asList(messages);
	}
	
	public BatchRequest(List<Request> messages){
		this.requests = messages;
	}
	
}
