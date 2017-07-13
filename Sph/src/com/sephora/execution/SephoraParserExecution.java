package com.sephora.execution;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.sephora.work.*;

public class SephoraParserExecution {

	public static void main(String[] args) {

		if (args.length != 1) {
			Help helper = new Help();
		} else {
			// 1. read mertics
			FileWorker fw = new FileWorker(args[0]);
			ArrayList<String> metrics = fw.readTransactions();

			ArrayList<String> formedString;

			// 2. open file
			PrintWriter myPW = fw.openResultFile();// PrintWriter

			// 3. parsing
			for (String metric : metrics) {
				formedString = Parser.parseJSON(metric);
				for (int i = 0; i < formedString.size(); i++) {
					fw.writeToResultfile(myPW, formedString.get(i));
				}
			}
		}
	}

}
