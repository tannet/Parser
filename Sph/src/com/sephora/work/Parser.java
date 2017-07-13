package com.sephora.work;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {
	static ArrayList<String> resultSet;
	static String metricName;
	static String jsonMetricPart;
	static String serverIP;
	static String timestamp;

	static String[] dataForRU = { "current_heap_used", "current_heap_size", "max_heap_size", "delta_current_heap_size",
			"delta_max_heap_size", "current_sessions", "peak_sessions", "max_sessions", "delta_peak_sessions",
			"delta_max_sessions", "current_native_processes", "peak_native_processes", "max_native_processes",
			"delta_peak_processes", "delta_max_processes", "current_db_connections", "peak_db_connections",
			"max_db_connections", "delta_peak_db_connections", "delta_max_db_connections", "current_async_threads_used",
			"current_async_threads_size", "max_async_threads_size", "delta_current_async_threads_size",
			"delta_max_async_threads_size" };

	static String[] dataForDCS_NPS = { "requestCount", "requestMeanTime", "requestTimeoutCount", "buildTimeMean",
			"busyCount", "idleCount", "type" };

	public static ArrayList<String> parseJSON(String metric) {
		resultSet = new ArrayList<>();
		String[] dataForMetric;

		if (metric.contains("ResourceUsage")) {
			dataForMetric = dataForRU;
		} else {
			dataForMetric = dataForDCS_NPS;
		}

		metricName = metric.split("\\|")[0];
		jsonMetricPart = metric.split("\\|")[1];
		serverIP = metric.split("\\|")[2];
		timestamp = metric.split("\\|")[3];

		JSONObject obj = new JSONObject(jsonMetricPart);

		JSONArray arr = obj.getJSONArray("data");
		for (int i = 0; i < dataForMetric.length; i++) {
			resultSet.add(metricName + "." + dataForMetric[i] + "." + serverIP + "\t"
					+ arr.getJSONObject(0).get(dataForMetric[i]) + "\t" + timestamp + "\n");
			// System.out.println(metricName + "." + dataForMetric[i] + "." +
			// serverIP + "\t"
			// + arr.getJSONObject(0).get(dataForMetric[i]) + "\t" + timestamp);
		}

		return resultSet;
	}

}
