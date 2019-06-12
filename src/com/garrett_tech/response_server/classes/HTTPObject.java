package com.garrett_tech.response_server.classes;

import java.util.Date;

public class HTTPObject {
	
	private Request request = new Request();
	private Response response = new Response();
	
	private StreamObject streamObject = new StreamObject();

	private Debug debug = new Debug();
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public StreamObject getStreamObject() {
		return streamObject;
	}

	public void setStreamObject(StreamObject streamObject) {
		this.streamObject = streamObject;
	}

	public Debug getDebug() {
		return debug;
	}

	public void setDebug(Debug debug) {
		this.debug = debug;
	}	
}
