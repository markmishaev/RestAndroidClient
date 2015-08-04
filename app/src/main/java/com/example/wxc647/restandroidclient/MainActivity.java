package com.example.wxc647.restandroidclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
{
    private List<User> usersCollection = new ArrayList<>();
    private HttpHelper httpHelper = new HttpHelper();
    private MiscUtil miscUtil = new MiscUtil();
    private UserInterfaceUtil uiUtil = new UserInterfaceUtil();

    final String serverUrl = "http://10.110.64.78:8080/users";


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

    public void updateUser(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hello Mark, What would you like me to show?").setTitle("Just a title");

        builder.setPositiveButton(R.string.dialog_message_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        builder.setNegativeButton(R.string.dialog_message_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void deleteUser(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enter a user id as it stored in the users table:").setTitle("Delete user");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.dialog_message_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String deletedUserId = input.getText().toString();
                //execute delete request for the given user
                new HttpDeleteRequestTask(MainActivity.this, deletedUserId).execute();
            }
        });

        builder.setNegativeButton(R.string.dialog_message_cancel,null);


        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask(this).execute();
    }

/**************************************HttpRequest Async Tasks Classes*****************************************************************************************************/
    private class HttpDeleteRequestTask extends AsyncTask<Void, Void,  Integer>
    {
        private String userId;
        private Context mContext;
        private UserInterfaceUtil uiUtil = new UserInterfaceUtil();

        public HttpDeleteRequestTask(Context context, String userId)
        {
            this.mContext = context;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected  Integer doInBackground(Void... params)
        {
            try
            {
                return httpHelper.deleteUserRequest(miscUtil.getUserUri(userId, usersCollection));
            }
            catch (Exception e)
            {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer responseCode)
        {
            //The server has fulfilled the request but does not need to return an entity-body
            if(responseCode == ResponseCodes.RESPONSE_CODE_204)
            {
                uiUtil.showAlert(this.mContext, "User has been successfully deleted", "RestApiClient");
            }
        }
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
                return   httpHelper. getUsersList(serverUrl, usersCollection);
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
            if(users != null && users.size() > 0)
            {
                ListView usersListView = (ListView) findViewById(R.id.listView);
                ArrayAdapter<User> adapter = new ArrayAdapter<>(this.mContext,
                        android.R.layout.simple_list_item_1, users);

                usersListView.setAdapter(adapter);
            }
        }
    }
}


