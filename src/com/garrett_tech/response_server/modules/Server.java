package com.garrett_tech.response_server.modules;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.garrett_tech.response_server.classes.HTTPObject;
import com.garrett_tech.response_server.etc.Constants;
import com.garrett_tech.response_server.etc.Utils;

public class Server implements Runnable {

	// Client Connection via Socket Class
	private Socket connect;
	private static List<HTTPObject> listOfLogs = new ArrayList<HTTPObject>();

	public Server(Socket c) {
		connect = c;
	}

	public static void main() {
		try {
			ServerSocket serverConnect = new ServerSocket(Constants.PORT);
			System.out
					.println("Server started.\nListening for connections on port : "
							+ Constants.PORT + " ...\n");

			// we listen until user halts server execution
			while (true) {
				Server myServer = new Server(serverConnect.accept());

				// create dedicated thread to manage the client connection
				Thread thread = new Thread(myServer);
				thread.start();
			}

		} catch (IOException e) {
			System.err.println("Server Connection error : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		HTTPObject httpObject = new HTTPObject();

		try {
			httpObject.getStreamObject().setSocket(connect);

			httpObject = Utils.processIncomingSocket(httpObject);

			// we support only GET and HEAD methods, we check
			//if (httpObject.getRequest().getHeader_method().equals("GET")
			//		|| httpObject.getRequest().getHeader_method().equals("POST")) {

				httpObject = doWork(httpObject);

				httpObject = Utils.processOutgoingSocket(httpObject);

				if (Constants.verbose) {
					System.out.println("File " + httpObject.getResponse().getHeader_path()
							+ " of type " + httpObject.getResponse().getHeader_contentType()
							+ " returned");
				}
			//}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpObject.getStreamObject().closeAllStreams();
		}
	}

	private HTTPObject doWork(HTTPObject httpObject) {

		boolean debugRequest;

		try {
			httpObject = Debug.debugRouter(httpObject, listOfLogs);

			debugRequest = httpObject.getDebug().isDebug();

			if (debugRequest == true) {
				return httpObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			String path = httpObject.getRequest().getHeader_path();
			path = path.replace("/Main", "");
			path = path.replace("/", "");
			
			int queryStart = path.indexOf("?");
			
			if(queryStart != -1)
			{
				path = path.substring(0, queryStart);
			}

			String fullPath = Constants.WEB_ROOT + "/" + path;
			File file = new File(fullPath);

			if (file.exists() && file.isDirectory()) {
				File fileObject = Utils.lastFileModified(fullPath);

				httpObject.getResponse().setHeader_responseCode("200");
				
				String extension = "";

				int i = fileObject.getName().lastIndexOf('.');
				if (i > 0) {
				    extension = fileObject.getName().substring(i+1);
				}
				
				httpObject.getResponse().setHeader_contentType("text; charset=UTF-8");
				
				if(extension.equalsIgnoreCase("json"))
				{
					httpObject.getResponse().setHeader_contentType("application/json");
				}
				
				if(extension.equalsIgnoreCase("xml"))
				{
					httpObject.getResponse().setHeader_contentType("text/xml; charset=UTF-8");
				}
				
				String relativePath = httpObject.getRequest().getHeader_path();
				
				if(relativePath.equalsIgnoreCase("/"))
				{
					relativePath =  relativePath + fileObject.getName();
				}
				else
				{
					relativePath =  relativePath + "/" + fileObject.getName();
				}
				
				httpObject.getResponse().setHeader_path(relativePath);
				httpObject.getDebug().setPath(fileObject.getAbsolutePath());
				httpObject.getResponse().setData(Files.readAllBytes(fileObject.toPath()));

				listOfLogs.add(httpObject);

			} else {
				httpObject.getResponse().setHeader_responseCode("404");
				httpObject.getResponse().setHeader_contentType("text/html; charset=UTF-8");
				listOfLogs.add(httpObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpObject.getResponse().setHeader_responseCode("500");
			httpObject.getResponse().setHeader_contentType("text/html; charset=UTF-8");
			listOfLogs.add(httpObject);
		}

		return httpObject;
	}

}