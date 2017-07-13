package com.burberry.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FileWorker {
	StringBuilder builder = new StringBuilder();
	String fileName;
	final static String RESULT_FILE_NAME = "burberry_result_";
	final static String RESULT_FILE_COLUMNS = "Timestamp, Timestamp#2, TX_NAME, Connection time, Response time, IP of responded server";

	public FileWorker(String argument) {
		setFileName(argument);
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(Calendar.getInstance().getTime());

	public ArrayList<String> readTransactions() {
		ArrayList<String> lines = new ArrayList<>();
		String currentLine = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((currentLine = bufferedReader.readLine()) != null) {
				if (currentLine.startsWith("http://") || currentLine.startsWith("https://")) {
					lines.add(currentLine);
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		return lines;
	}

	public PrintWriter openResultFile() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(RESULT_FILE_NAME + timeStamp + ".csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		builder.setLength(0);
		builder.append(RESULT_FILE_COLUMNS + "\n");
		pw.write(builder.toString());
		pw.flush();
		return pw;
	}
	
	public void writeToResultfile(PrintWriter pw, String resultToWrite){
		builder.setLength(0);
		builder.append(resultToWrite);
		pw.write(builder.toString());
		pw.flush();
	}

	public void closeResultFile(PrintWriter pw) {
		pw.flush();
		pw.close();
	}
}
