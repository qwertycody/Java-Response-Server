package com.garrett_tech.response_server.application;

import com.garrett_tech.response_server.etc.Constants;
import com.garrett_tech.response_server.modules.Client;
import com.garrett_tech.response_server.modules.Server;

public class Program {

	public static void main(String[] args) throws Exception {
		try {
			setArguments(args);

			Thread thread = new Thread("Response Server Thread") {
				public void run() {
					Server.main();
				}
			};

			thread.start();

			Thread.sleep(3000);
			
			String protocol = "http";
			String hostname = "localhost";
			String port = Integer.toString(Constants.PORT);
			String path = "ExampleFolder";
			String fullUrl = protocol + "://" + hostname + ":" + port + "/" + path;
			
			Client.get(fullUrl);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setArguments(String[] args) {
		System.out.println("Running with the following Arguments:");

		for (String arg : args) {
			System.out.println(arg);

			if (arg.contains(Constants.arg_response_directory)) {
				arg = arg.replace(Constants.arg_response_directory, "");
				Constants.WEB_ROOT = arg;
			}

			if (arg.contains(Constants.arg_port)) {
				arg = arg.replace(Constants.arg_port, "");
				Constants.PORT = Integer.parseInt(arg);
			}

			if (arg.contains(Constants.arg_verbose)) {
				arg = arg.replace(Constants.arg_verbose, "");
				Constants.verbose = Boolean.parseBoolean(arg);
			}
		}
	}
}