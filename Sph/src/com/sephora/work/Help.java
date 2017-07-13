package com.sephora.work;

public class Help {
	public Help() {
		this.printHelp();
	}

	private void printHelp() {
		System.out.println("Start the program with name of file argument, for example:");
		System.out.println(">java -jar Sephora_ParserForGrafana.jar results.txt");
	}
}
