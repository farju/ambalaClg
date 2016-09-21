package in.ace.pardeep.org.acev2;


import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPassword extends Fragment {

EditText rollNumber,emailAddress;
    Button submitButton;
    View view;
    Button buttonToBack;
    private static boolean jobCancel=false;
    
    public ForgotPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_forgot_password, container, false);
        
        rollNumber=(EditText)view.findViewById(R.id.editTextRollNoForgotPassword);
        emailAddress=(EditText)view.findViewById(R.id.editTextEmailForgotPassword);
        buttonToBack=(Button)view.findViewById(R.id.backButtonForgot);
        submitButton=(Button)view.findViewById(R.id.buttonForgotPassword);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                onDestroy();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyData();
            }
        });
        
        return view;
    }

    private void verifyData() {
        
        if(!rollNumber.getText().toString().equalsIgnoreCase("") && !emailAddress.getText().toString().equalsIgnoreCase("")){
            if(verifyEmail(emailAddress.getText().toString())){
                sendDataToServser(rollNumber.getText().toString(),emailAddress.getText().toString());
            }
            else {
                Toast.makeText(getActivity(), "Incorrect Email!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(),"Empty fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataToServser(String rollnumber, String email) {
        jobCancel=false;
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait.");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                jobCancel = true;
                System.out.println(jobCancel);
            }
        });
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("rollno",rollnumber);
        hashMap.put("emailid",email);
        JSONObject jsonObject=new JSONObject(hashMap);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://aceapp-pardeep16.rhcloud.com/student/api/forgotpassword",jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                if(!jobCancel) {
                    System.out.println("Response.................." + jsonObject);
                    showResponse(jsonObject);
                    rollNumber.setText("");
                    emailAddress.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue request= Volley.newRequestQueue(getActivity());
        request.add(jsonObjectRequest);
        
    }

    private void showResponse(JSONObject jsonObject) {
        try {
            String msg=jsonObject.getString("message").toString();
            showAlertDialog(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAlertDialog(String msg) {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle("Response");
        alertBuilder.setMessage(msg);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog1=alertBuilder.create();
        alertDialog1.show();
    }

    private boolean verifyEmail(String email) {
        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
