package in.ace.pardeep.org.acev2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacementFragment extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private View view=null;
    private TextView textView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HomeScreen homeScreen;
    String result="";
    public PlacementFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_placement);
        sharedPreferences=this.getSharedPreferences("placement", Context.MODE_PRIVATE);

        Button buttonToBack=(Button)findViewById(R.id.backButton);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView=(TextView)findViewById(R.id.textViewPlacementFragment);
        textView.setVisibility(View.GONE);
        listView=(ListView)findViewById(R.id.placementFragmentListView);
        listView.setVisibility(View.GONE);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.pullToRefreshPlacementFragment);
        swipeRefreshLayout.setOnRefreshListener(this);
        sendRequestToServer();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                sendRequestToServer();
            }
        });
    }


    private void sendRequestToServer() {
        String url=ScriptUrl.placementUrl;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                swipeRefreshLayout.setRefreshing(false);
                textView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
              //  updateToSharePreference(s);
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
                    textView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    serverResponse(result);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String getDataFromFile() {
            String readData=null;
        try {
            String res = null;
            InputStream inputStream=this.openFileInput(Files.fileName);
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

   /* private void saveDataToFile(String s) {
        try {
            File file = new File(getActivity().getFilesDir(), Files.fileName);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(getActivity().openFileOutput(Files.fileName,0));
            //JSONObject jsonObject=new JSONObject(s);
            String update=s.toString();
            System.out.println(update);
            outputStreamWriter.write(s.toString());
            outputStreamWriter.close();
            System.out.println("File save.....");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("File not save.....");
        }
    }*/

  /*  private void updateToSharePreference(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            sharedPreferences=getActivity().getSharedPreferences("Placement",Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
            editor.putString("placementList",jsonObject.toString());
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/

   /* private String getPlacementSharePreference() {
        sharedPreferences=getActivity().getSharedPreferences("Placement",Context.MODE_PRIVATE);
        String result=sharedPreferences.getString("placementList",null);
        return result;
    }
*/
    private void serverResponse(String s) {
        ParseJSONList parseJSONList=new ParseJSONList(s);
        parseJSONList.parseJSON();
        PlacementListAdapter placementListAdapter=new PlacementListAdapter(ParseJSONList.description,ParseJSONList.date);
        listView.setAdapter(placementListAdapter);

    }


    @Override
    public void onRefresh() {
        System.out.println("refrshing......");
        textView.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        sendRequestToServer();
    }
}
