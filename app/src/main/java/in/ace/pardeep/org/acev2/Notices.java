package in.ace.pardeep.org.acev2;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Notices extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String response="";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        Button buttonToBack=(Button)findViewById(R.id.backButton);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView=(ListView)findViewById(R.id.noticeFragmentListView);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.pullToRefreshNoticeFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notices, menu);
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

    private void sendRequestToServer() {
        // progressBar=(ProgressBar)view.findViewById(R.id.noticeProgressBar);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,ScriptUrl.getNoticeUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //  progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                //textView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                // addToSharePreference(s);
                serverResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                //  progressBar.setVisibility(View.GONE);
                sharedPreferences=getSharedPreferences("NoticePref", Context.MODE_PRIVATE);

                /*if(sharedPreferences.contains("noticeList")) {
                    response = getSharedNoticeList();
                    if(response.length()>0){
                        textView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        serverResponse(response);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();

                }*/
                String result=getDataFromNoticeFile();
                if(result!=null) {
                    //textView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    serverResponse(result);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(Notices.this, "Network Problem!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void serverResponse(String s) {
        ParseJsonListNotices parseJsonListNotices=new ParseJsonListNotices(s);
        parseJsonListNotices.parseJSON();
        NoticesListAdapter noticesListAdapter=new NoticesListAdapter(ParseJsonListNotices.description,ParseJsonListNotices.date);
        listView.setAdapter(noticesListAdapter);
    }


    private String getDataFromNoticeFile() {
        String read=null;
        try {
            String readData;
            InputStream inputStream=this.openFileInput(Files.fileNotice);
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

    @Override
    public void onRefresh() {
        listView.setVisibility(View.GONE);
        sendRequestToServer();
    }
}
