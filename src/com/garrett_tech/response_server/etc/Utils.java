package com.garrett_tech.response_server.etc;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.garrett_tech.response_server.classes.HTTPObject;

public class Utils {

	public static HTTPObject processIncomingSocket(HTTPObject httpObject)
			throws Exception {

		if (httpObject.getStreamObject().getSocket() != null) {
			BufferedReader in_bufferedReader = null;

			// we manage our particular client connection
			// we read characters from the client via input stream on the socket
			in_bufferedReader = new BufferedReader(new InputStreamReader(
					httpObject.getStreamObject().getSocket().getInputStream()));

			httpObject.getStreamObject()
					.setIn_BufferedReader(in_bufferedReader);

			// Set Input Stream Object

			InputStream in_inputStream = null;

			in_inputStream = httpObject.getStreamObject().getSocket()
					.getInputStream();

			httpObject.getStreamObject().setIn_inputStream(in_inputStream);

			// Set Input Stream Reader

			// get first line of the request from the client
			String input = in_bufferedReader.readLine();
			// we parse the request with a string tokenizer
			if (input != null) {
				StringTokenizer parse = new StringTokenizer(input);
				httpObject.getRequest().setHeader_method(
						parse.nextToken().toUpperCase());
				httpObject.getRequest().setHeader_path(parse.nextToken());

				StringBuilder request_Headers = new StringBuilder();
				String line;

				// Credit to JCGonzalez with their implementation
				// (http://www.jcgonzalez.com/java-socket-mini-server-http-example)

				int postDataI = -1;

				while ((line = in_bufferedReader.readLine()) != null
						&& (line.length() != 0)) {
					request_Headers.append(line);
					request_Headers.append("<br />");

					if (line.indexOf("Content-Length:") > -1) {
						postDataI = new Integer(line.substring(
								line.indexOf("Content-Length:") + 16,
								line.length())).intValue();
					}
				}

				httpObject.getRequest().setHeader(request_Headers.toString());

				StringBuilder request_Data = new StringBuilder();
				String postData = "";
				// read the post data
				if (postDataI > 0) {
					char[] charArray = new char[postDataI];
					in_bufferedReader.read(charArray, 0, postDataI);
					postData = new String(charArray);

					request_Data.append(postData);
				}

				httpObject.getRequest().setData(
						request_Data.toString().getBytes());
			}

		}
		return httpObject;
	}

	public static HTTPObject processOutgoingSocket(HTTPObject httpObject)
			throws Exception {
		if (httpObject.getStreamObject().getSocket() != null
				&& httpObject.getStreamObject().getSocket().isClosed() != true) {

			PrintWriter out = null;
			BufferedOutputStream dataOut = null;

			// we get character output stream to client (for headers)
			out = new PrintWriter(httpObject.getStreamObject().getSocket()
					.getOutputStream());

			httpObject.getStreamObject().setOut(out);

			// get binary output stream to client (for requested data)
			dataOut = new BufferedOutputStream(httpObject.getStreamObject()
					.getSocket().getOutputStream());

			httpObject.getStreamObject().setDataOut(dataOut);

			if (out != null && dataOut != null) {
				StringBuilder headers = new StringBuilder();

				// send HTTP Headers
				String responseCode = httpObject.getResponse()
						.getHeader_responseCode();
				out.println(responseCode);
				headers.append(responseCode + "<br />");

				String server = httpObject.getResponse().getHeader_server();
				out.println(server);
				headers.append(server + "<br />");

				String date = httpObject.getRequest().getHeader_date()
						.toString();
				out.println(date);
				headers.append(date + "<br />");

				String contentType = httpObject.getResponse()
						.getHeader_contentType();
				out.println(contentType);
				headers.append(contentType + "<br />");

				String contentLength = httpObject.getResponse()
						.getHeader_contentLength();
				out.println(contentLength);
				headers.append(contentLength + "<br />");
				
				httpObject.getResponse().setHeader(headers.toString());

				// Blank Line Needed per HTTP Specification on where the payload
				// AFTER header starts
				out.println();

				out.flush(); // flush character output stream buffer
				dataOut.write(httpObject.getResponse().getData(), 0, httpObject
						.getResponse().getData().length);
				dataOut.flush();
			}
		}
		return httpObject;
	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}
}