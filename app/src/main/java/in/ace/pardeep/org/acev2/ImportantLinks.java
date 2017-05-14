package in.ace.pardeep.org.acev2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ImportantLinks extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String response="";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_links);

        Button buttonToBack=(Button)findViewById(R.id.backButton);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView=(ListView)findViewById(R.id.LinksListView);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.pullToRefreshLinks);
        //swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) getActivity());
        swipeRefreshLayout.setOnRefreshListener(this);
        sendRequestToServer();

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                listView.setVisibility(View.GONE);
                sendRequestToServer();
            }
        });

    }

    private void sendRequestToServer() {
        System.out.println("request");

        StringRequest request=new StringRequest(Request.Method.GET,ScriptUrl.getImpLinks(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                swipeRefreshLayout.setRefreshing(false);
                /*textView.setVisibility(View.VISIBLE);*/
                listView.setVisibility(View.VISIBLE);

                showResponseImpLinks(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(ImportantLinks.this, "Network Problem", Toast.LENGTH_SHORT).show();
                String result=getDataFromImpLinks();
                if(result!=null){
                  // progressBar.setVisibility(View.GONE);
                    //showResponseNoticeList(result);
                    swipeRefreshLayout.setRefreshing(true);
                    /*textView.setVisibility(View.VISIBLE);*/
                    listView.setVisibility(View.VISIBLE);
                    showResponseImpLinks(result);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private String getDataFromImpLinks() {
        String read=null;
        try {
            String readData;
            InputStream inputStream=openFileInput(Files.fileImpLinks);
            if(inputStream!=null){
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder=new StringBuilder();
                while((readData=bufferedReader.readLine())!=null){
                    stringBuilder.append(readData+"\n");
                    read=readData;
                }
            }

        }
        catch (java.io.FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return read;
    }

    private void showResponseImpLinks(String s) {

        if (s.equalsIgnoreCase("No Links to display")) {
            Toast.makeText(ImportantLinks.this, "Server Under Working Try After Sometime!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            /*ParseJsonListNotices parse = new ParseJsonListNotices(s);
            parse.parseJSON();*/

            //  NoticesListAdapter notice = new NoticesListAdapter(ParseJsonListNotices.description, ParseJsonListNotices.date);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("links");

                String[] urls = new String[jsonArray.length()];
                String[] description = new String[jsonArray.length()];
                JSONObject childObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    childObject = jsonArray.getJSONObject(i);
                    String url = childObject.getString("url");
                    String content = "<a href=" + url + ">Click Here </a>";
                    urls[i] = content;
                    description[i] = childObject.getString("message");

                }

                LinksAdapter linksAdapter = new LinksAdapter(urls, description);

                listView.setAdapter(linksAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_important_links, menu);
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
    public void onRefresh() {
        listView.setVisibility(View.GONE);
        sendRequestToServer();
    }
}
