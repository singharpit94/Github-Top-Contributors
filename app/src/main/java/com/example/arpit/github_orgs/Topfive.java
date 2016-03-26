package com.example.arpit.github_orgs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by arpit on 26/3/16.
 */
public class Topfive extends AppCompatActivity {

   TextView t1,t2,t3,t4,t5;
    String s=null;
    ArrayList<Name> arrayList;
    String org="lugnitdgp";
    String url;
    String repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topfive);

     t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);

        Intent intent = getIntent();
        if (null != intent) {
            org = intent.getStringExtra("org");

        }

        SendMessage s1=new SendMessage();
        s1.execute();
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(Topfive.this, Topthree.class);
                repo=t1.getText().toString();
                myintent.putExtra("repo",repo );
                myintent.putExtra("org",org );
                startActivity(myintent);
                finish();
            }
        });


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
    public class SendMessage extends AsyncTask<String, String, String> {

        private Dialog loadingDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loadingDialog = ProgressDialog.show(Topfive.this, "Please wait", "Searching...");
        }
        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;

            // Setting the name Value Pairs.

            String result =null;
            // Setting up the connection inside the try and catch block.
            try {
                //Setting up the default HttpClient
                HttpClient httpClient = new DefaultHttpClient();

                //Setting up the http post method and passing the url in case
                //of online database and the ip address in case of local database.
                //And the php files which serves as the link between the android app
                //and mysql database.
                url="https://api.github.com/orgs/"+org+"/repos";
                HttpGet httpPost = new HttpGet(url);

                //Passing the newValuePairs inside the httpPost.


                //Getting the response
                HttpResponse response = httpClient.execute(httpPost);

                //Setting up the entity
                HttpEntity entity = response.getEntity();


                //Setting up the content inside the input stream reader.
                //Lets define the input stream reader (defined above)
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = " ";
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                result = sb.toString();
                s=result;

            }
            catch (ClientProtocolException e) {
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
                //fun1();
            } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
               // fun1();
            } catch (IOException e) {
                Log.e("Log_tag", "IO Exception");
                e.printStackTrace();
               // fun1();
            }



            return result;
        }



        @Override
        protected void onPostExecute(String result) {
            if(result==null)
            {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "ERROR IN CONNECTION", Toast.LENGTH_LONG).show();

            }
            else{             s = result.trim();

                loadingDialog.dismiss();
              //  Toast.makeText(getApplicationContext(),"Search Complete",Toast.LENGTH_LONG).show();
                parseData();}





        }
    }
    //This method will parse json data
    private void parseData() {
         arrayList=new ArrayList<>();
         JSONArray array=null;
        try
        {
            array=new JSONArray(s);
        } catch (JSONException e) {
           e.printStackTrace();
           // fun1();
        }
        if(array!=null) {
            for (int i = 0; i < array.length(); i++) {
                //Creating the superhero object
                Name stationary = new Name();
                JSONObject json = null;
                try {
                    //Getting json
                    json = array.getJSONObject(i);

                    //Adding data to the superhero object

                    stationary.setname(json.getString("name"));

                    stationary.setForks(json.getInt("forks"));
                    // Toast.makeText(getApplicationContext(),json.getString("name"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    // e.printStackTrace();
                    fun1();
                }
                //Adding the superhero object to the list
                arrayList.add(stationary);
            }

            fun();
        }
        else
        {
            fun1();
        }
//Notifying the adapter that data has been added or changed

    }
    void fun()
    {

        Collections.sort(arrayList);
        Name a=arrayList.get(0);
        t1.setText(a.getname());
        Name a1=arrayList.get(1);
        t2.setText(a1.getname());
        Name a2=arrayList.get(2);
        t3.setText(a2.getname());
        Name a3=arrayList.get(3);
        t4.setText(a3.getname());
        Name a4=arrayList.get(4);
        t5.setText(a4.getname());



    }

    void fun1()
    {
        Toast.makeText(getApplicationContext(),"Invalid Organisation Name Try Again .....",Toast.LENGTH_LONG).show();
    }

}
