package com.burberry.work;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class ObjURL {
	String URLInfo;

	long startTime;
	long endTime;
	URL curUrl;
	URLConnection currUrlConn;

	public String sendRequest(String url) throws IOException {
		String result = "";

		String[] currentUrlInfo = new String[6];

		startTime = System.currentTimeMillis();
		curUrl = new URL(url);
		currUrlConn = curUrl.openConnection();
		endTime = System.currentTimeMillis() - startTime;

		currentUrlInfo[3] = String.valueOf(endTime);

		currUrlConn.setRequestProperty("Pragma",
				"akamai-x-get-client-ip, akamai-x-cache-on, akamai-x-cache-remote-on, akamai-x-check-cacheable, akamai-x-get-cache-key, akamai-x-get-extracted-values, akamai-x-get-nonces, akamai-x-get-ssl-client-session-id, akamai-x-get-true-cache-key, akamai-x-serial-no, akamai-x-feo-trace, akamai-x-get-request-id");

		try {
			startTime = System.currentTimeMillis();
			currUrlConn.getContent();
			endTime = System.currentTimeMillis() - startTime;
			currentUrlInfo[4] = String.valueOf(endTime);
			InetAddress address = InetAddress.getByName(curUrl.getHost());
			String ip = address.getHostAddress();
			currentUrlInfo[5] = ip;
			
			Map<String, List<String>> map = currUrlConn.getHeaderFields();

			List<String> checkXCache = map.get("X-Cache");
			List<String> sessionInfo = map.get("X-Akamai-Session-Info");

			if (checkXCache == null || sessionInfo == null) {
				System.out.println("No such header. Check .setRequestProperty()");
			} else {
				for (String header : checkXCache) {
					currentUrlInfo[2] = Parser.parseTrName(header, url);
				}
				for (String header : sessionInfo) {
					if (header.contains("name=G2CAE_DATA;")) {
						currentUrlInfo[0] = Parser.parseTimestamp((header));
						long num = Long.parseLong(Parser.parseTimestamp((header)));
						double arythm = ((double) num) / 86400000 + 25569.000 + 0.125;
						currentUrlInfo[1] = String.valueOf(arythm);
					}
				}
			}
			for (int i = 0; i < currentUrlInfo.length; i++) {
				if (i == 0) {
					result = "";
				}
				result += currentUrlInfo[i];
				if (i != currentUrlInfo.length - 1) {
					result += ",";
				} else {
					result += "\n";
				}
			}
		} catch (java.net.UnknownHostException e) {
		}
		return result;
	}

}
