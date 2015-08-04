package com.example.wxc647.restandroidclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask(this).execute();
    }

    public String getUsersCollectionResponse(String originalResponse)
    {
        return originalResponse.substring(originalResponse.indexOf( '[' ), originalResponse.indexOf( ']' )+1);
    }

    public  List<User> getUsersList(String urlParam)
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

            List<User> usersCollection = new ArrayList<>();
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

    private class HttpRequestTask extends AsyncTask<Void, Void,  List<User>>
    {
        private Context mContext;

        public HttpRequestTask(Context context)
        {
            this.mContext = context;
        }

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected  List<User> doInBackground(Void... params)
        {
            try
            {
                final String url = "http://192.168.1.56:8080/users";
                return getUsersList(url);
            }
            catch (Exception e)
            {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<User> users)
        {
            ListView usersListView = (ListView) findViewById(R.id.listView);
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this.mContext,
                    android.R.layout.simple_list_item_1, users);

            usersListView.setAdapter(adapter);

        }
    }

    /*
    private class HttpRequestTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... params)
        {
            try
            {
                final String url = "http://10.110.64.54:8080/users";


                //Create a Rest template
                RestTemplate restTemplate = new RestTemplate();

                List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

                //Add the Jackson Message converter
                messageConverters.add(new MappingJackson2HttpMessageConverter());

                //Add the message converters to the restTemplate
                restTemplate.setMessageConverters(messageConverters);

                //List resultList = Arrays.asList(restTemplate.getForObject(url, User[].class));
                //List<User> users = restTemplate.getForObject(url ,UsersResponse.class).getUsers();


                //RestTemplate restTemplate = new RestTemplate();
                //MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                //converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
                //restTemplate.getMessageConverters().add(converter);
                //Iterable<User> users = restTemplate.getForObject(url, Iterable.class);


                if(users != null)
                {
                    int x = 5;
                }

                return users;
            }
            catch (Exception e)
            {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<User> users)
        {
            if(users != null)
            {
                int y = 6;
            }
        }
    }*/
}


