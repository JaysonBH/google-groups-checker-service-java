package com.demo.workspace.directory.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleAPIWrapper {
    private static final Logger logger = LoggerFactory.getLogger(GoogleAPIWrapper.class);

    public static String makeHttpRequest(String accessToken, String urlString) throws IOException {
		logger.info("Making Requests on: {}", urlString);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Add the Authorization header with the Bearer token
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Accept", "application/json"); //Added to specify the content type.

        // Get the response code.
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Read the response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            // Handle error responses (e.g., 400, 401, 500)
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String line;
                StringBuilder errorResponse = new StringBuilder();
                if(connection.getErrorStream() != null) {
                    while ((line = errorReader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    return "Error Response Code: " + responseCode + "  " + errorResponse.toString(); // Return the error response.
                }
                else{
                    return "Error Response Code: " + responseCode + "  No error message returned";
                }

            }
            //throw e;  //Re-throw the original exception if you want the caller to handle it.
        } finally {
            connection.disconnect();
        }
    }

}
