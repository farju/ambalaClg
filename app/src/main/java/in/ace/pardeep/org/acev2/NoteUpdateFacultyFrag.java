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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
public class NoteUpdateFacultyFrag extends Fragment {

    private static String[] semesterList={"Select Semester..","1st","2nd","3rd","4th","5th","6th","7th","8th"};
    Spinner spinner;
    EditText editTextUpload,editTextTitleContent;
    Button buttonToUpload,buttonToBack;
    View view;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static int positionDept=0;

    private static String department;
    private static String facultyId;
    private static String deptId;

    public NoteUpdateFacultyFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_note_update_faculty, container, false);
        spinner=(Spinner)view.findViewById(R.id.spinnerUploadNote);
        editTextUpload=(EditText)view.findViewById(R.id.editTextUploadNote);
        editTextTitleContent=(EditText)view.findViewById(R.id.editTextTitle);
        buttonToUpload=(Button)view.findViewById(R.id.buttonUpload);
        buttonToBack=(Button)view.findViewById(R.id.backButton);

        sharedPreferences=getActivity().getSharedPreferences(FacultyPortal.getPrefFaculty(), 0);
        editor=sharedPreferences.edit();
        department=sharedPreferences.getString("department", null);
        facultyId=sharedPreferences.getString("facultyid",null);
        deptId=sharedPreferences.getString("departmentId",null);
       // Toast.makeText(getActivity(), ""+department+"\n"+facultyId, Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,semesterList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionDept = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadContent();
            }
        });

        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                onDestroy();
            }
        });

        return view;
    }

    private void uploadContent() {
        if(positionDept!=0){
            if(editTextUpload.getText().toString().length()>0 && editTextTitleContent.getText().toString().length()>0){
                String semester=semesterList[positionDept];
                String content=editTextUpload.getText().toString();
                /*Toast.makeText(getActivity(), "Sem :"+semester+"\n Content :"+content+"\n dept"+department+"\n id"+facultyId, Toast.LENGTH_SHORT).show();
            */
            String title=editTextTitleContent.getText().toString();
                uploadToServer(semester,content,title);

            }
            else {
                Toast.makeText(getActivity(), "Empty fields!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Please Select Semester!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadToServer(String semester, String content, String title) {
       // Toast.makeText(getActivity(), ""+semester+"\n"+content+"\n"+title+"\n"+facultyId+"\n"+deptId+"\n"+department, Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Updating");
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        String url="http://aceapp-pardeep16.rhcloud.com/faculty/api/textnote?facultyId="+facultyId+"&deptId="+deptId;

        HashMap<String,String> hashMap=new HashMap<String, String>();
        hashMap.put("semester",semester);
        hashMap.put("content",content);
        hashMap.put("title",title);


        JSONObject jsonObject=new JSONObject(hashMap);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                System.out.println("Response.................." + jsonObject);
                showResponse(jsonObject);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Network Error!", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue request= Volley.newRequestQueue(getActivity());
        request.add(jsonObjectRequest);

    }

    private void showResponse(JSONObject jsonObject) {
        boolean success=false;
        try {
           success= jsonObject.getBoolean("success");
            if(success){
                editTextTitleContent.setText("");
                editTextUpload.setText("");
                buildDialog(success,jsonObject.getString("message").toString());
            }
            else {
                buildDialog(success,jsonObject.getString("message").toString());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void buildDialog(boolean success, String message) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Update Message");
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }


}
