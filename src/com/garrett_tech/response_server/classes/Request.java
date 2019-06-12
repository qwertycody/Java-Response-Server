package com.garrett_tech.response_server.classes;

import java.util.Date;

public class Request {
	private String header_method = "";
	private Date header_date = new Date();
	private String header_contentType = "";
	private String header_contentLength = "";
	private String header_path = "";
	private String header = "";
	private byte[] data = new byte[0];
	
	public String getHeader_method() {
		return header_method;
	}
	public void setHeader_method(String header_method) {
		this.header_method = header_method;
	}
	public Date getHeader_date() {
		return header_date;
	}
	public void setHeader_date(Date header_date) {
		this.header_date = header_date;
	}
	public String getHeader_contentType() {
		return header_contentType;
	}
	public void setHeader_contentType(String header_contentType) {
		this.header_contentType = "Content-Type: " + header_contentType;
	}
	public String getHeader_contentLength() {
		return "Content-Length: " + data.length;
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
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}	
	
	public int getId() {
		String id = header_path;
		
		id = id.replace("debug", "");
		id = id.replace("request", "");
		id = id.replace("response", "");
		id = id.replace("/", "");

		try {
			return Integer.parseInt(id);
		} catch (Exception e) {
			return -1;
		}
	}
}
