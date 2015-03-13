package fr.jerome.climbinggymlog.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.helpers.LoginHelper;

public class LoginActivity extends Activity implements View.OnClickListener {

    public static final String URL_QUERY = "http://climbinggymstats.olympe.in/php/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonLogin = (Button) findViewById(R.id.login_button);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        new GetClients().execute(URL_QUERY);

//        if (v.getId() == R.id.login_button) {
//
//            createTmpClient();
//
//            LoginHelper loginHelper = new LoginHelper();
//        }
    }

    private void createTmpClient() {

        String nom = ((EditText) findViewById(R.id.login_nom)).getText().toString();
        String prenom = ((EditText) findViewById(R.id.login_prenom)).getText().toString();
        String numClient = ((EditText) findViewById(R.id.login_num_client)).getText().toString();
        String email = ((EditText) findViewById(R.id.login_email)).getText().toString();
    }

    // AsyncTask to get the server response and refresh the EditText in the UI thread
    private class GetClients extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> messages = new ArrayList<String>();
        @Override
        protected ArrayList<String> doInBackground(String... url) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;

            // Get the Request answer into a StringBuilder
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(url[0]));
                // Http message
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("error", e.getMessage());
            }
            finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            // Parse Json in a ArrayList
//            try {
//                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject object = jsonArray.getJSONObject(i);
//                    messages.add(object.getString("message"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return messages;
        }
        @Override protected void onPostExecute(ArrayList<String> s) {

            Log.d("test", messages.toString());
        }
    }
}
