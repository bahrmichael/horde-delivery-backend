package de.bahr;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michaelbahr on 23/03/16.
 */
@Deprecated
public class Http {

    private static final String USER_AGENT = "http://hordedelivery.com Rihan Shazih";

    // HTTP GET request
    @Deprecated
    public static BasicDBObject asJson(String url, String method) throws Exception {
        StringBuffer response = getRequest(url, method);

        //print result
        return (BasicDBObject) JSON.parse(response.toString());

    }

    @Deprecated
    public static StringBuffer getRequest(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod(method.toUpperCase());

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }
}
