package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Query {
    public static void test() {
        System.out.println("uh oh");
        try {
            // Set up the POST request
            URL url = new URL("http://155.248.226.28/script.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Send the username and password in the request body
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("username=example_user&password=example_password");
            writer.flush();

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            // Print the response
            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

