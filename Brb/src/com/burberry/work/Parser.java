package com.burberry.work;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	public static String protocolS = "https://";

	private static String name_01 = "BURBAKAMAI_HTTPHIT";
	private static String name_02 = "BURBAKAMAI_HTTPMISS";
	private static String name_03 = "BURBAKAMAI_HTTPSHIT";
	private static String name_04 = "BURBAKAMAI_HTTPSMISS";

	public static String parseTrName(String header, String url) {
		String tcp = header.split(" ")[0];
		String resultTrName = "";

		switch (tcp) {
		case "TCP_HIT":
			if (url.contains(protocolS)) {
				resultTrName = name_03;
			} else {
				resultTrName = name_01;
			}
			break;
		case "TCP_MISS":
			if (url.contains(protocolS)) {
				resultTrName = name_04;
			} else {
				resultTrName = name_02;
			}
			break;
		default:
			resultTrName = "Couldn't define transaction name";
		}
		return resultTrName;
	}

	public static String parseTimestamp(String header) {
		Pattern p = Pattern.compile("time=(\\d+)(?:\\s)req_id");
		Matcher m = p.matcher(header);
		if (m.find()) {
			return m.group(1);
		} else {
			return "No timestamp";
		}
	}

}
