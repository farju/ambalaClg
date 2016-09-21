package in.ace.pardeep.org.acev2;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FacultyChangePassword extends Fragment {

    EditText oldPassword,newPassword,confirmPassword;
    Button changePasswordButton,buttonToBack;
    View view;
    private static boolean jobCancel=false;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    public FacultyChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_faculty_change_password, container, false);

        oldPassword=(EditText)view.findViewById(R.id.editTextOldPassword);
        newPassword=(EditText)view.findViewById(R.id.editTextNewPassword);
        confirmPassword=(EditText)view.findViewById(R.id.editTextConfirmPassword);
        changePasswordButton=(Button)view.findViewById(R.id.buttonChangePassword);
        buttonToBack=(Button)view.findViewById(R.id.backButtonChangePassword);

        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                onDestroy();
            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyData();
            }
        });

        return view;
    }
    private void verifyData() {
        if(!oldPassword.getText().toString().equalsIgnoreCase("") && !newPassword.getText().toString().equalsIgnoreCase("") && !confirmPassword.getText().toString().equalsIgnoreCase("")){
            if (newPassword.getText().toString().equals(confirmPassword.getText().toString())){
                sendDataToServer(oldPassword.getText().toString(),newPassword.getText().toString(),confirmPassword.getText().toString());
            }
            else {
                Toast.makeText(getActivity(), "Password n't matched!Try Again", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Empty fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataToServer(String oldpassword, String newpassword, String confirmpassword) {

        jobCancel=false;
        sharedPreference=getActivity().getSharedPreferences(FacultyPortal.getPrefFaculty(),0);
        String emailid=sharedPreference.getString("email",null);
        String facultyname=sharedPreference.getString("facultyname",null);
        String url="http://aceapp-pardeep16.rhcloud.com/faculty/api/faculty/changepassword";
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
        hashMap.put("oldpassword",oldpassword);
        hashMap.put("newpassword",newpassword);
        hashMap.put("emailid",emailid);
        hashMap.put("name",facultyname);
        JSONObject jsonObject=new JSONObject(hashMap);
        System.out.println(jsonObject);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                if(!jobCancel) {
                    System.out.println("Response.................." + jsonObject);
                    showResponse(jsonObject);
                    oldPassword.setText("");
                    newPassword.setText("");
                    confirmPassword.setText("");
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
        alertBuilder.setTitle("Message");
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


}
