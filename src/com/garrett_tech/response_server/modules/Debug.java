package com.garrett_tech.response_server.modules;

import java.util.List;

import com.garrett_tech.response_server.classes.HTTPObject;
import com.garrett_tech.response_server.classes.Request;
import com.garrett_tech.response_server.classes.Response;

public class Debug {
	public static HTTPObject debugRouter(HTTPObject httpObject,
			List<HTTPObject> listOfLogs) {
		String path = httpObject.getRequest().getHeader_path();
		int id = httpObject.getRequest().getId();

		HTTPObject retrievedObject = new HTTPObject();
		
		if(id != -1)
		{
			retrievedObject = listOfLogs.get(id);
		}
		
		if (path.contains("/debug") == false) {
			httpObject.getDebug().setDebug(false);
			return httpObject;
		}

		if (path.contains("/request") == true) {
			Request request = retrievedObject.getRequest();
			httpObject.getResponse().copyTo(null, request);
			httpObject.getResponse().setHeader_contentType("text/plain");
			httpObject.getDebug().setDebug(true);
			return httpObject;
		}

		if (path.contains("/response") == true) {
			Response response = retrievedObject.getResponse();
			httpObject.getResponse().copyTo(response, null);
			httpObject.getResponse().setHeader_contentType("text/plain");
			httpObject.getDebug().setDebug(true);
			return httpObject;
		}

		httpObject = mainDebugScreen(httpObject, listOfLogs);
		httpObject.getDebug().setDebug(true);
		return httpObject;
	}
	
	private static HTTPObject mainDebugScreen(HTTPObject httpObject,
			List<HTTPObject> listOfLogs) {
		httpObject.getResponse().setHeader_responseCode("200");
		httpObject.getResponse().setHeader_contentType(
				"text/html; charset=UTF-8");

		String dataToDisplay = "<meta http-equiv=\"refresh\" content=\"5\">";

		for (int id = 0; id < listOfLogs.size(); id++) {

			StringBuilder stringBuilder = new StringBuilder();
			HTTPObject logEntry = listOfLogs.get(id);
			
			stringBuilder.append(getRowString("Mock Response File Path:",
					logEntry.getDebug().getPath(), false));
			stringBuilder.append(getRowString("Request Time:", logEntry.getRequest()
					.getHeader_date().toString(), false));
			stringBuilder.append(getRowString("Request Path:", logEntry
					.getRequest().getHeader_path(), false));
			stringBuilder.append(getRowString("Request Headers:", logEntry
					.getRequest().getHeader(), false));
			stringBuilder.append(getFullDebugRow("Request Data:", "request", id));
			stringBuilder.append(getRowString("Response Headers:", logEntry
					.getResponse().getHeader(), false));
			stringBuilder.append(getFullDebugRow("Response Data:", "response", id));
			

			String table = "<table border='1'>" + stringBuilder.toString()
					+ "</table>";
			dataToDisplay = table + "<br />" + dataToDisplay;
		}

		httpObject.getResponse().setData(
				new String("<html><body>" + dataToDisplay + "</body></html>")
						.getBytes());

		return httpObject;
	}

	private static String getFullDebugRow(String text, String endpoint,
			int id_substring) {
		String link = "/debug/" + Integer.toString(id_substring) + "/"
				+ endpoint;
		text = getLink(text, link);
		String value = getIframe("", link);
		String row = getRowString(text, value, false);
		return row;
	}

	private static String getIframe(String text, String link) {
		return "<iframe src=" + '"' + link + '"' + ">" + text
				+ "<iframe></iframe>";
	}

	private static String getLink(String text, String link) {
		return "<a href=" + '"' + link + '"' + ">" + text + "</a>";
	}

	private static String getRowString(String label, String value,
			boolean header) {
		if (header == true) {
			return "<tr><th>" + label + "</th><th>" + value + "</th></tr>";
		} else {
			return "<tr><td>" + label + "</td><td>" + value + "</td></tr>";
		}
	}

}
