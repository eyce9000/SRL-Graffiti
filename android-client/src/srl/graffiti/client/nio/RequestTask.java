package srl.graffiti.client.nio;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import srl.graffiti.messages.BatchRequest;
import srl.graffiti.messages.BatchResponse;

import android.os.AsyncTask;

import com.grl.json.client.Client;
import com.grl.json.client.exceptions.ClientException;
import com.grl.json.messages.Request;
import com.grl.json.messages.Response;

public class RequestTask extends AsyncTask<Request, Long, List<Response>>{
	private Client client;
	private Callback<List<Response>> callback;
	public RequestTask(Client client, Callback<List<Response>> callback){
		this.client = client;
		this.callback = callback;
	}
	
	@Override
	protected List<Response> doInBackground(Request... requests) {
		Request request;
		if(requests.length == 1){
			request = requests[0];
			Response response = client.sendRequest(request);
			List<Response> list = new LinkedList<Response>();
			list.add(response);
			return list;
		}
		else if(requests.length > 1){
			request = new BatchRequest(requests);
			BatchResponse responses = client.sendRequest(request,BatchResponse.class);
			return responses.getResponses();
		}
		else{
			return new LinkedList<Response>();
		}
	}
	
	@Override
	protected void onPostExecute(List<Response> responses){
		this.callback.onCallback(responses);
	}
}
