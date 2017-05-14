package in.ace.pardeep.org.acev2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private ListView listView;
    private static String requestUrl="https://api.myjson.com/bins/54cqe";
   // private ProgressBar progressBar;
    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String response="";
    public NoticesFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_notices, container, false);
        //sharedPreferences= getActivity().getSharedPreferences("NoticePref", Context.MODE_PRIVATE);
        textView=(TextView)view.findViewById(R.id.textViewNoticesFragment);
        textView.setVisibility(View.GONE);
        listView=(ListView)view.findViewById(R.id.noticeFragmentListView);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.pullToRefreshNoticeFragment);
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
        return view;
    }


    private void sendRequestToServer() {
       // progressBar=(ProgressBar)view.findViewById(R.id.noticeProgressBar);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,ScriptUrl.getNoticeUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
              //  progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                textView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
               // addToSharePreference(s);
                serverResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

              //  progressBar.setVisibility(View.GONE);
                sharedPreferences=getActivity().getSharedPreferences("NoticePref",Context.MODE_PRIVATE);
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
                    textView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    serverResponse(result);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();

                }
                }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private String getSharedNoticeList() {
        sharedPreferences=getActivity().getSharedPreferences("NoticePref",Context.MODE_PRIVATE);
        String result=sharedPreferences.getString("noticeList", null);
        return result;
    }

    private void addToSharePreference(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            sharedPreferences=getActivity().getSharedPreferences("NoticePref",Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
            editor.putString("noticeList",jsonObject.toString());
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void serverResponse(String s) {
        ParseJsonListNotices parseJsonListNotices=new ParseJsonListNotices(s);
        parseJsonListNotices.parseJSON();
        NoticesListAdapter noticesListAdapter=new NoticesListAdapter(ParseJsonListNotices.description,ParseJsonListNotices.date);
        listView.setAdapter(noticesListAdapter);
    }

    @Override
    public void onRefresh() {
        textView.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        sendRequestToServer();
    }
    private String getDataFromNoticeFile() {
        String read=null;
        try {
            String readData;
            InputStream inputStream=getActivity().openFileInput(Files.fileNotice);
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
}
