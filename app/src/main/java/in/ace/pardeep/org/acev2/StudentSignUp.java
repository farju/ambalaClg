package in.ace.pardeep.org.acev2;


import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSignUp extends Fragment {

    View view;
    Spinner spinnerCourses,spinnerDepartments,spinnerSemester;

    EditText userName,userRollNo,userEmail;
    Button signupButton,buttonToBack;

    private static String course,departmentSelected,semesterTaken,deptId;

private static String[] courses={"B.Tech","M.Tech"};
    private static String[] departmentList={"Select department..","Biotechnology Engineering","Computer Science & Engineering","Electronics and Communication Engineering","Mechanical Enginnering"};
    private static String[] semesterList={"Select Current Semester","1st","2nd","3rd","4th","5th","6th","7th","8th"};
    private static Integer[] deptIds={0,2,3,4,5};
    private boolean jobCancel=false;

    public StudentSignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_student_sign_up, container, false);
        spinnerCourses=(Spinner)view.findViewById(R.id.spinnerCourseTakenStudentSignUp);
        spinnerDepartments=(Spinner)view.findViewById(R.id.spinnerDepartmentStudentSignUp);
        spinnerSemester=(Spinner)view.findViewById(R.id.spinnerSemesterStudentSignUp);

        userName=(EditText)view.findViewById(R.id.editTextUserNameStudentSignUp);
        userRollNo=(EditText)view.findViewById(R.id.editTextRollNoStudentSignUp);
        userEmail=(EditText)view.findViewById(R.id.editTextEmailIdStudentSignUp);

        signupButton=(Button)view.findViewById(R.id.buttonRegisterStudentSignUp);
        buttonToBack=(Button)view.findViewById(R.id.backButton);

        alertShowDialog();

        ArrayAdapter<String> coursesAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,courses);
        ArrayAdapter<String> departmentsAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,departmentList);
        ArrayAdapter<String> semesterAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,semesterList);

        spinnerCourses.setAdapter(coursesAdapter);
        spinnerDepartments.setAdapter(departmentsAdapter);
        spinnerSemester.setAdapter(semesterAdapter);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUserData();
            }
        });

        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentSelected = departmentList[position];
                deptId= String.valueOf(deptIds[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course=courses[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterTaken=semesterList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                onDestroy();
            }
        });


        return  view;
    }

    private void verifyUserData() {
        if(!userName.getText().toString().equalsIgnoreCase("") && !userRollNo.getText().toString().equalsIgnoreCase("") && !userEmail.getText().toString().equalsIgnoreCase("")){
            if(!departmentSelected.equalsIgnoreCase("Select department..")){
                if(!semesterTaken.equalsIgnoreCase("Select Current Semester")){
                    if(verifyEmail(userEmail.getText().toString())){
                        sendToServer(userName.getText().toString(),userRollNo.getText().toString(),userEmail.getText().toString(),course,departmentSelected,semesterTaken,deptId);
                    }
                    else {
                        Toast.makeText(getActivity(), "Enter valid Email!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please select your current semester!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), "Please select your department!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Please fill all details!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendToServer(String name, String rollno, String emailid, String course, String departmentSelected, String semesterTaken,String deptId) {
        System.out.println(name);
        System.out.println(rollno);
        System.out.println(emailid);
        System.out.println(course);
        System.out.println(departmentSelected);
        System.out.println(semesterTaken);
        System.out.println(deptId);

        jobCancel=false;
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Register details");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressDialog.cancel();
                jobCancel=true;
            }
        });

        String url="http://xdeveloper.royalwebhosting.net/login.php";

        // final String obj=new Gson().toJson(hashMap);

       /* StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
                   }
        }) {


        };*/


        HashMap<String,String> hashMap=new HashMap<String, String>();
        hashMap.put("name", name);
        hashMap.put("rollno",rollno);
        hashMap.put("emailid",emailid);
        hashMap.put("course",course);
        hashMap.put("department",departmentSelected);
        hashMap.put("semester",semesterTaken);
        hashMap.put("deptid",deptId);
        JSONObject jsonObject=new JSONObject(hashMap);


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://aceapp-pardeep16.rhcloud.com/student/api/student/signup",jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                if(!jobCancel) {
                    System.out.println("Response.................." + jsonObject);
                    showResponse(jsonObject);
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
            System.out.println(jsonObject);
            String msg=jsonObject.getString("message");
            showAlertDialog(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAlertDialog(String msg) {
        AlertDialog.Builder alertBuild=new AlertDialog.Builder(getActivity());
        alertBuild.setTitle("Message");
        alertBuild.setMessage(msg);
        alertBuild.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuild.setCancelable(false);
        AlertDialog alert=alertBuild.create();
        alert.show();

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

    private void alertShowDialog() {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle("Alert");
        alertBuilder.setMessage("Please enter your correct information.On registering the details you will provide username & password in your registered email.\n\nIf you have any query or can't signup contact to admin.");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertBuilder.create();
        alert.show();
    }


}
