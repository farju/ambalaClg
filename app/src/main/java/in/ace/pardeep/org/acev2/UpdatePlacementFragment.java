package in.ace.pardeep.org.acev2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePlacementFragment extends Fragment {

    View view;
    EditText titleText;
    EditText descriptionText;
    DatePicker datePicker;
    Button submitButton;
    private static String currentDate="";



    public UpdatePlacementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_update_placement, container, false);
        titleText=(EditText)view.findViewById(R.id.editTextTitlePlacement);
        descriptionText=(EditText)view.findViewById(R.id.editTextDescriptionPlacement);
        datePicker=(DatePicker)view.findViewById(R.id.datePicker);
        submitButton=(Button)view.findViewById(R.id.submitButtonPlacement);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleText.getText().toString().length()>0 && descriptionText.getText().toString().length()>0){
                    storeData();
                }
                else {
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void storeData() {
        int yearS=datePicker.getYear();
        int monthS=datePicker.getMonth()+1;
        int dayS=datePicker.getDayOfMonth();
        currentDate=""+yearS+"-"+monthS+"-"+dayS;
        if(currentDate.length() >0){
            sendRequestToServer();
        }
        System.out.println("Date is........... :" + currentDate);
    }

    private void sendRequestToServer() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,ScriptUrl.getPlacementListUpdate(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                serverRespose(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"Network Error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("Title",titleText.getText().toString());
                param.put("Description",descriptionText.getText().toString());
                param.put("Date",currentDate);
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void serverRespose(String s) {
        Toast.makeText(getActivity(),"Response :"+s,Toast.LENGTH_SHORT).show();
        titleText.setText("");
        descriptionText.setText("");
    }


}
