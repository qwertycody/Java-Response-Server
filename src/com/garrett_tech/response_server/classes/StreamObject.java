package com.garrett_tech.response_server.classes;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class StreamObject {
	private BufferedReader in_bufferedReader;
	private InputStream in_inputStream;
	private PrintWriter out;
	private BufferedOutputStream dataOut;
	private Socket socket;
	
	public void closeAllStreams() {
		try {
			dataOut.close();
		} catch (Exception e) {
		}
		try {
			in_bufferedReader.close();
		} catch (Exception e) {
		}
		
		try {
			in_inputStream.close();
		} catch (Exception e) {
		}
		
		try {
			out.close();
		} catch (Exception e) {
		}
		try {
			socket.close();
		} catch (Exception e) {
		}
	}

	public BufferedReader getIn_BufferedReader() {
		return in_bufferedReader;
	}

	public void setIn_BufferedReader(BufferedReader in) {
		this.in_bufferedReader = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public BufferedOutputStream getDataOut() {
		return dataOut;
	}

	public void setDataOut(BufferedOutputStream dataOut) {
		this.dataOut = dataOut;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public InputStream getIn_inputStream() {
		return in_inputStream;
	}

	public void setIn_inputStream(InputStream in_inputStream) {
		this.in_inputStream = in_inputStream;
	}
}
