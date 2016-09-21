package in.ace.pardeep.org.acev2;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadAssignments extends Fragment implements DownloadAssignmentsAdapter.CustomButtonListener {

    View view;

    private long enqueue;
    public DownloadManager downloadManager;
    DownloadComplete downloadComplete;

    public NetworkInfo wifi,mobile;
    ConnectivityManager connectivityManager;
    public static boolean isConnected=false;
    ListView listView;
    Button buttonToBack;

    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;

    ArrayList<DownloadAssignmentParameters> arrayList=null;


    public DownloadAssignments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_download_assignments, container, false);
        /*button=(Button)view.findViewById(R.id.dowmloadButton);*/
        downloadComplete=new DownloadComplete();
        getActivity().registerReceiver(downloadComplete,new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*downloadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse("http://aceapp-pardeep16.rhcloud.com/upload/faculty/files/CSE_7sem.pdf"));


            *//*
                String path= Environment.getExternalStorageDirectory()+"/Download";
                File file=new File(path);
                if(!file.exists()){
                    file.mkdirs();
                }




                String servicestring = Context.DOWNLOAD_SERVICE;
                downloadManager = (DownloadManager) getActivity().getSystemService(servicestring);
                Uri uri = Uri
                        .parse("http://aceapp-pardeep16.rhcloud.com/upload/faculty/files/CSE_7sem.pdf");
                Toast.makeText(getActivity(), "Starting Download.!", Toast.LENGTH_SHORT).show();

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setDescription("Assignments Download.!");
                request.setDestinationInExternalPublicDir("/Download", "syllabus.pdf");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);*/

                /*try {
                    downloadManager.openDownloadedFile(reference);
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }*/


          /* // }
        });*/


        listView=(ListView)view.findViewById(R.id.listViewAssignments);
        buttonToBack=(Button)view.findViewById(R.id.backButton);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                onDestroy();
            }
        });
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.pullToRefreshFragment);

        sendRequestToServer();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
checkConnectivity();


        return view;
    }

    private void refreshContent() {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                sendRequestToServer();
            }
        });
    }

    private void sendRequestToServer() {

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        sharedPreferences=getActivity().getSharedPreferences(StudentPortal.getSharedPrefProfile(), 0);
        String department=sharedPreferences.getString("department", null);
        String semester=sharedPreferences.getString("semester",null);
        String url="http://aceapp-pardeep16.rhcloud.com/student/api/assignments?semester="+semester+"&deptId="+department;

        System.out.println(url);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                serverResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Network Error!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void serverResponse(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            if(jsonArray.length()>0){
                arrayList=new ArrayList<DownloadAssignmentParameters>();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject childObject=jsonArray.getJSONObject(i);
                    String name=childObject.getString("name").toString();
                    String content=childObject.getString("content").toString();
                    String date=childObject.getString("date").toString();
                    String faculty=childObject.getString("facultyname").toString();
                    String url=childObject.getString("url").toString();
                    String type=childObject.getString("filetype").toString();
                    arrayList.add(new DownloadAssignmentParameters(name,date,faculty,url,content,type));
                }

                DownloadAssignmentsAdapter downloadAssignmentAdapter=new DownloadAssignmentsAdapter();
                downloadAssignmentAdapter.setCustomButtonAssignmentListener(getActivity(),DownloadAssignments.this,listView);
                DownloadAssignmentsAdapter.setAssignmentParameterses(arrayList);
                listView.setAdapter(downloadAssignmentAdapter);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onButtonClick(int position, String url, String filename) {
        System.out.println(url);
        System.out.println(filename);


        if(isConnected) {
            String servicestring = Context.DOWNLOAD_SERVICE;
            downloadManager = (DownloadManager) getActivity().getSystemService(servicestring);
            Uri uri = Uri
                    .parse(url);
            Toast.makeText(getActivity(), "Starting Download..", Toast.LENGTH_SHORT).show();

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDescription("Assignments Download.!");
            request.setDestinationInExternalPublicDir("/Download", filename);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Long reference = downloadManager.enqueue(request);
        }
        else {
            Toast.makeText(getActivity(), "Check your Connection and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadComplete extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "Download Complete!", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    public void getDownloadFile(String url,String filename){
        /*downloadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(url));*/

        url=url.replaceAll(" ","%20");
        System.out.println(url);
        System.out.println(filename);
        checkConnectivity();

        if(isConnected) {

            String servicestring = Context.DOWNLOAD_SERVICE;
            downloadManager = (DownloadManager) getActivity().getSystemService(servicestring);
            Uri uri = Uri
                    .parse(url);
            Toast.makeText(getActivity(), "Starting Download..", Toast.LENGTH_SHORT).show();

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDescription("Assignments Download.!");
            request.setDestinationInExternalPublicDir("/Download", filename);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Long reference = downloadManager.enqueue(request);
        }
        else {
            Toast.makeText(getActivity(), "Check your Connection and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkConnectivity() {
        connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        isConnected=(wifi!=null && wifi.isConnected())||(mobile!=null && mobile.isConnected());
    }

}
