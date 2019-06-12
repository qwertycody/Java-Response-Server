package com.garrett_tech.response_server.classes;

public class Response {
	private String header_contentType = "";
	private String header_contentLength = "";
	private String header_server = "Server: Java SOAP and JSON Response Server from Cody Garrett : 1.0";
	private String header_responseCode = "";
	private String header_path = "";
	private String header = "";
	private byte[] data = new byte[0];

	public String getHeader_contentType() {
		return header_contentType;
	}

	public void setHeader_contentType(String header_contentType) {
		this.header_contentType = "Content-Type: " + header_contentType;
	}

	public String getHeader_contentLength() {
		return "Content-Length: " + data.length;
	}

	public String getHeader_responseCode() {
		return header_responseCode;
	}

	public void setHeader_responseCode(String header_responseCode) {
		
		if(header_responseCode.equalsIgnoreCase("404"))
		{
			this.header_responseCode = "HTTP/1.1 404 File Not Found";
			return;
		}
		
		if(header_responseCode.equalsIgnoreCase("200"))
		{
			this.header_responseCode = "HTTP/1.1 200 OK";
			return;
		}		
		
		this.header_responseCode = header_responseCode;
	}

	public String getHeader_path() {
		return header_path;
	}

	public void setHeader_path(String header_path) {
		this.header_path = header_path;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getHeader_server() {
		return header_server;
	}

	public void setHeader_server(String header_server) {
		this.header_server = header_server;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public void copyTo(Response response, Request request)
	{
		if(response != null)
		{
			this.data = response.getData();
			this.header = response.getHeader();
			this.header_contentLength = response.getHeader_contentLength();
			this.header_contentType = response.getHeader_contentType();
			this.header_path = response.getHeader_path();
			this.header_responseCode = response.getHeader_responseCode();
		}
		
		if(request != null)
		{
			this.data = request.getData();
			this.header = request.getHeader();
			this.header_contentLength = request.getHeader_contentLength();
			
			if(request.getHeader_contentType() == null || request.getHeader_contentType().trim().length() == 0)
			{
				setHeader_contentType("text/html");
			}
			else
			{
				this.header_contentType = request.getHeader_contentType();
			}
			this.header_path = request.getHeader_path();
			setHeader_responseCode("200");
		}
	}
}
