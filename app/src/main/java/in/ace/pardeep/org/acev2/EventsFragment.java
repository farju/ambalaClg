package in.ace.pardeep.org.acev2;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private static ListView listView;
    View eventView;
    private static ArrayList<EventsListContent> arrayList=null;
    TextView textview;

    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;
    EventsListViewAdapter eventsListViewAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        eventView=inflater.inflate(R.layout.fragment_events, container, false);
        textview=(TextView)eventView.findViewById(R.id.errorTextView);
        listView=(ListView)eventView.findViewById(R.id.listViewEvents);

        swipeRefreshLayout=(SwipeRefreshLayout)eventView.findViewById(R.id.pullToRefreshEventsFragment);


       // sendRequestToServer();
        swipeRefreshLayout.setRefreshing(true);
        refreshContent();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });





        return eventView;
    }

    private void refreshContent() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                System.out.println("Refreshing.....");
                textview.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                sendRequestToServer();
            }
        });
    }

    private void sendRequestToServer() {

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching..");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET,ScriptUrl.getEventsGetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
               progressDialog.dismiss();
                listView.setVisibility(View.VISIBLE);
                textview.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                serverResponse(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                textview.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                textview.setText("Connection Error Try Again!\n Pull to Refresh");
                if(arrayList!=null) {
                    if (arrayList.size() > 0) {
                        textview.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Connection Error!", Toast.LENGTH_SHORT).show();
                        if(listView!=null){
                            eventsListViewAdapter=new EventsListViewAdapter(getActivity());
                            eventsListViewAdapter.setContentArrayList(arrayList);
                            listView.setAdapter(eventsListViewAdapter);
                        }
                    }
                }


                textview.setTextSize(25);

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void serverResponse(String s) {

        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("response");
            if(jsonArray.length()>0) {
                System.out.println(jsonArray.length());
                arrayList=new ArrayList<EventsListContent>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject childObject = jsonArray.getJSONObject(i);
                    String titleGet = childObject.getString("title");
                    String dateGet = childObject.getString("date");
                    String urlGet = childObject.getString("url");
                    if (urlGet.length()==0){
                        System.out.println("url null");
                        urlGet="";
                    }
                    String descriptionGet = childObject.getString("description");
                    System.out.println(titleGet);
                    System.out.println(dateGet);
                    System.out.println(urlGet);
                    System.out.println(descriptionGet);
                    arrayList.add(new EventsListContent(titleGet,dateGet,urlGet,descriptionGet));
                }
                eventsListViewAdapter=new EventsListViewAdapter(getActivity());
                eventsListViewAdapter.setContentArrayList(arrayList);
                if(listView!=null){
                    listView.setAdapter(eventsListViewAdapter);
                }

            }
            else {
                Toast.makeText(getActivity(), "No Data To Display!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
