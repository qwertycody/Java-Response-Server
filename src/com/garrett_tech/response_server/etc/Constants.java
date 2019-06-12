package com.garrett_tech.response_server.etc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constants {
	public static String WEB_ROOT = ".";
	public static int PORT = 8080;
	public static boolean verbose = true;
	
	public static final String arg_response_directory = "--response-directory=";
	public static final String arg_port = "--port=";
	public static final String arg_verbose = "--verbose=";


	public static List<String> getArgs() {
		return new ArrayList<String>() {
			{
				this.add(arg_response_directory);
			}
		};
	}
}