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
public class DownloadsStudentPortal extends Fragment {

    ListView listView;
    View view;
    Button buttonToBack;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<DownloadStudentsQues> arrayList=null;

    SharedPreferences sharedPreferences;
    private static String semester;
    private static String department;
    public DownloadsStudentPortal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_downloads_student_portal, container, false);
        listView=(ListView)view.findViewById(R.id.listViewDownloadNote);
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
        department=sharedPreferences.getString("department",null);
        semester=sharedPreferences.getString("semester",null);
        String url="http://aceapp-pardeep16.rhcloud.com/student/api/questions?semester="+semester+"&deptId="+department;

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
        //Toast.makeText(getActivity(), ""+s, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            arrayList=new ArrayList<DownloadStudentsQues>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject childObject=jsonArray.getJSONObject(i);
                String title=childObject.getString("title").toString();
                String content=childObject.getString("content").toString();
                String date=childObject.getString("date").toString();
                String name=childObject.getString("facultyname").toString();
                arrayList.add(new DownloadStudentsQues(title,date,content,name));
            }
            DownloadStudentQuesAdapter downloadStudentQuesAdapter=new DownloadStudentQuesAdapter();
            DownloadStudentQuesAdapter.setDownloadStudentsQuesArrayList(arrayList);
            listView.setAdapter(downloadStudentQuesAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
