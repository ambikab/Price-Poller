package org.edu.aub282.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * @author ambika_b
 *
 */
public class RESTClient {

	public static final String key;

	static {
		Properties prop = new Properties();
		InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("/credentials.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		key = prop.getProperty("key");
	}

	public static String httpGet(String urlStr) {
		URL url;
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200) return null; 
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null)
				sb.append(line);
			rd.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/*public static void main(String[] args) throws IOException {
		//System.out.println(httpGet("http://api.zappos.com/Search?term=boots&filters={%22productId%22:[%227983185%22],%22-percentOff%22:[%220%22,%221%22,%222%22,%223%22,%224%22,%225%22,%226%22,%227%22,%228%22,%229%22,%2210%22,%2211%22,%2212%22,%2213%22,%2214%22,%2215%22,%2216%22,%2217%22,%2218%22,%2219%22,%2220%22,%2221%22,%2222%22,%2223%22,%2224%22]}&facets=[%22percentOff%22]&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73"));
		String resultString = httpGet("http://api.zappos.com/Search/term/boots?limit=100&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73");
		SearchResultsDTO results = (SearchResultsDTO) JsonParser.convertJsonToObject(resultString, SearchResultsDTO.class);
		System.out.println(results);
	}*/
}
