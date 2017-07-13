package com.burberry.execution;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.burberry.work.FileWorker;
import com.burberry.work.Help;
import com.burberry.work.ObjURL;

public class Main {
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			Help helper = new Help();
		} else {
			// 1. read urls
			FileWorker fw = new FileWorker(args[0]);
			ArrayList<String> urls = fw.readTransactions();// ArrayList<String>

			// 2. open file
			PrintWriter myPW = fw.openResultFile();// PrintWriter

			// 3. cycle for open them: create url obj, send 2 requests, get
			// result string, write this string to file
			while (true) {
			for (String url : urls) {
				// 3.1. create obj
				ObjURL myURL = new ObjURL();
				// 3.2. send 2 req
				fw.writeToResultfile(myPW, myURL.sendRequest(url));
				fw.writeToResultfile(myPW, myURL.sendRequest(url + "&" + System.currentTimeMillis()));
				// 3.3. write result to file
				 }
			}

			// 4.close file - impossible
			//fw.closeResultFile(myPW);
		}
	}

}
