package in.ace.pardeep.org.acev2;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class News extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    Button buttonToBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    String result="";
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        
        listView=(ListView)findViewById(R.id.newsListView);
        buttonToBack=(Button)findViewById(R.id.backButton);

        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.pullToRefreshNewsFragment);
        swipeRefreshLayout.setOnRefreshListener(this);
        sendRequestForNews();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                sendRequestForNews();
            }
        });

    }

    private void sendRequestForNews() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET,ScriptUrl.getNewsUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                swipeRefreshLayout.setRefreshing(false);
                /*textView.setVisibility(View.VISIBLE);*/
                listView.setVisibility(View.VISIBLE);
                //  updateToSharePreference(s);
                saveDataToFile(s);
                serverResponse(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
              /*  sharedPreferences=getActivity().getSharedPreferences("Placement",Context.MODE_PRIVATE);
                if(sharedPreferences.contains("placementList")) {
                    response = getPlacementSharePreference();
                    int len=response.length();
                    System.out.println("res.............." + response);
                    if (len!=0) {
                        swipeRefreshLayout.setRefreshing(true);
                        textView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        serverResponse(response);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                    else{
                        swipeRefreshLayout.setRefreshing(false);
                    }*/
                result=getDataFromFile();
                if(result!=null){
                    swipeRefreshLayout.setRefreshing(true);
                    /*textView.setVisibility(View.VISIBLE);*/
                    listView.setVisibility(View.VISIBLE);
                    serverResponse(result);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(News.this,"Please Check Network Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void saveDataToFile(String s) {
        try{
            File file=new File(context.getFilesDir(),Files.fileNews);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(openFileOutput(Files.fileNews,0));
            outputStreamWriter.write(s.toString());
            outputStreamWriter.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void serverResponse(String s) {
        if (s.equalsIgnoreCase("Network Error")) {
            Toast.makeText(News.this, "Network Problem Please Try Again!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            /*ParseJsonListNotices parse = new ParseJsonListNotices(s);
            parse.parseJSON();*/

            //  NoticesListAdapter notice = new NoticesListAdapter(ParseJsonListNotices.description, ParseJsonListNotices.date);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("newslist");

                if(jsonArray.length()>0) {
                    String[] title = new String[jsonArray.length()];
                    String[] description = new String[jsonArray.length()];
                    String[] dates = new String[jsonArray.length()];
                    JSONObject childObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        childObject = jsonArray.getJSONObject(i);
                        title[i]=childObject.getString("title");
                        description[i] = childObject.getString("description");
                        dates[i]=childObject.getString("date");

                    }
                    NewsAdapter newsAdapter=new NewsAdapter(description,dates,title);


                    listView.setAdapter(newsAdapter);
                }
                else {
                    Toast.makeText(News.this, "No News to display", Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private String getDataFromFile() {
        String readData=null;
        try {
            String res = null;
            InputStream inputStream=this.openFileInput(Files.fileNews);
            System.out.println("file read");
            if(inputStream!=null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                System.out.println("file read");
                StringBuilder stringBuilder=new StringBuilder();
                while ((res=bufferedReader.readLine())!=null){
                    stringBuilder.append(res + "\n");
                    System.out.println(res);
                    readData=res;
                }
                inputStream.close();

            }
        }
        catch (java.io.FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return readData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
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
        sendRequestForNews();
    }
}
