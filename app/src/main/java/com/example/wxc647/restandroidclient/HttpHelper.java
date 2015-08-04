package com.example.wxc647.restandroidclient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by wxc647 on 8/4/2015.
 */
public class HttpHelper
{
    public int deleteUserRequest(String userUri)
    {
        HttpURLConnection conn = null;

        try
        {
            URL url = new URL(userUri);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            return responseCode;
        }
        catch (Exception e)
        {
            Log.e("MainActivity", e.getMessage(), e);
        }
        finally
        {
            if(conn != null)
            {
                conn.disconnect();
            }
        }

        return -1;
    }

    public List<User> getUsersList(String urlParam, List<User> usersCollection)
    {

        HttpURLConnection conn = null;
        try
        {

            URL url = new URL(urlParam);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            String usersCollectionResponse = getUsersCollectionResponse(response);

            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();
            JsonArray users = (JsonArray) jsonParser.parse(usersCollectionResponse);

            usersCollection.clear();
            for (int i=0; i < users.size(); i++)
            {
                User user = gson.fromJson(users.get(i), User.class);
                usersCollection.add(user);
            }

            return usersCollection;
        }
        catch (Exception e)
        {
            Log.e("MainActivity", e.getMessage(), e);
        }
        finally
        {
            if(conn != null)
            {
                conn.disconnect();
            }
        }

        return null;
    }

    public String getUsersCollectionResponse(String originalResponse)
    {
        return originalResponse.substring(originalResponse.indexOf( '[' ), originalResponse.indexOf( ']' )+1);
    }
}
