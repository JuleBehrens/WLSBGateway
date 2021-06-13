package com.SE3.Gateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * service class for HTTP-Requests
 */
public class HTTPClient {

    /**
     * Sends a GET-Request via HTTP to a given URL and returns the answer
     * <p>
     * Expects a JSON-Answer
     * <p>
     * Uses the HttpURLConnection class
     * @param url URL as String
     * @return HTTP answer as String or if an Error accurs Exception message
     */
    public static String getRequest(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            String response = "";
            while ((output = br.readLine()) != null) {
                response += output;
            }
            conn.disconnect();
            return response;
        } catch (Exception e) {
            return "Exception in NetClientGet: "+e.toString();
        }
    }
}
