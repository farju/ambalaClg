package in.ace.pardeep.org.acev2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplyOnline extends AppCompatActivity implements View.OnClickListener {

    Button datePicker;
    private static String[] departmentListSyllabus={"Select department..","Biotechnology Engineering","Computer Science & Engineering","Electronics and Communication Engineering","Mechanical Enginnering"};
    Spinner spinnerDepartmentList;
    String selectDepartment;
    EditText editName,editTextMarks,editTextRank,editTextAddress,editTextEmail,editTextContact;
    public static  EditText editTextDob;
    Button submitButton,buttonToBack;
    public static String dateOfbirth;
    private static boolean jobCancel=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_online);

        spinnerDepartmentList=(Spinner)findViewById(R.id.applyOnlineAce);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,departmentListSyllabus);
        spinnerDepartmentList.setAdapter(arrayAdapter);

        editName=(EditText)findViewById(R.id.editTextStudentApplyName);
        editTextMarks=(EditText)findViewById(R.id.marks);
        editTextAddress=(EditText)findViewById(R.id.postal_address);
        editTextContact=(EditText)findViewById(R.id.contact_no);
        editTextDob=(EditText)findViewById(R.id.editTextdob);
        editTextRank=(EditText)findViewById(R.id.jee);
        editTextEmail=(EditText)findViewById(R.id.email_address);
        submitButton=(Button)findViewById(R.id.submit);
        submitButton.setOnClickListener(this);
        buttonToBack=(Button)findViewById(R.id.backButtonApply);
        buttonToBack.setOnClickListener(this);



        spinnerDepartmentList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectDepartment=departmentListSyllabus[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        datePicker=(Button)findViewById(R.id.buttonCalenderPicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new DateFragment();
                dialogFragment.show(getSupportFragmentManager(), "datePicker");


            }
        });


        /*WebView webView=(WebView)findViewById(R.id.webApplyOnline);
        webView.setWebViewClient(new WebViewClient());
        String url="http://pardeep.my3gb.com/apply.html";
        webView.loadUrl(url);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apply_online, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==submitButton){
            onFormSubmit();
        }
        else if (v==buttonToBack){
            onBackPressed();
        }
    }

    private void onFormSubmit() {
        String name=editName.getText().toString();
        String marks=editTextMarks.getText().toString();
        String dept=selectDepartment;
        String contact=editTextContact.getText().toString();
        String dob=editTextDob.getText().toString();
        String address=editTextAddress.getText().toString();
        String email=editTextEmail.getText().toString();

        if(!name.equalsIgnoreCase("") && !marks.equalsIgnoreCase("") && !address.equalsIgnoreCase("") && !dob.equalsIgnoreCase("")){
            if(!dept.equalsIgnoreCase("Select department..")){
                if(contact.length()==10){
                    if(validateEmail(email)){
                        //Toast.makeText(ApplyOnline.this, "Your Form is submit!", Toast.LENGTH_SHORT).show();
                        sendDatatoServer(name,marks,dept,contact,dob,address,email);
                    }
                    else {
                        Toast.makeText(ApplyOnline.this, "Please Enter Valid Email!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ApplyOnline.this, "Please enter Correct Number!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(ApplyOnline.this, "Please Select Department!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(ApplyOnline.this, "Please fill all details!", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendDatatoServer(String name, String marks, String dept, String contact, String dob, String address, String email) {
        jobCancel=false;
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Submission Form");
        progressDialog.setMessage("Please twait");
        progressDialog.show();

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressDialog.cancel();
                jobCancel = true;
                System.out.println(jobCancel);
            }
        });

        String messagetosend="" +
                "<table>" +
                "<tr>" +
                "<th><td>Student Info</td><th></tr>" +
                "<tr><td>Student's Name:</td>" +
                "<td>"+name+" </td></tr>" +
                "<tr><td>Student's 12th Marks:</td>" +
                "<td>"+marks+" </td></tr>" +
                "<tr><td>Department Selected:</td>" +
                "<td>"+dept+" </td></tr>" +
                "<tr><td>Contact Number:</td>" +
                "<td>"+contact+" </td></tr>" +
                "<tr><td>D.O.B :</td>" +
                "<td>"+dob+" </td></tr>" +
                "<tr><td>Address :</td>" +
                "<td>"+address+" </td></tr>" +
                "<tr><td>Email :</td>" +
                "<td>"+email+" </td></tr>" +
                "<tr><td>JEE Rank:</td>" +
                "<td>"+editTextRank.getText().toString()+" </td></tr>" +
                "";

        System.out.println(messagetosend);


        HashMap<String,String> hashMap=new HashMap<String, String>();
        hashMap.put("message",messagetosend);
        hashMap.put("user","developer.aceapp@gmail.com");
        hashMap.put("subject","New Form Submission of  "+name);
        hashMap.put("type", "onlineform");
        JSONObject jsonObject=new JSONObject(hashMap);
System.out.println(jobCancel);
        System.out.println(jsonObject);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://139.59.74.116:3000/mail/sendmail",jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                if(!jobCancel) {
                    System.out.println("Response.................." + jsonObject);
                    showResponse(jsonObject);
                    editName.setText("");
                    editTextEmail.setText("");
                    editTextContact.setText("");
                    editTextDob.setText("");
                    editTextMarks.setText("");
                    editTextAddress.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(ApplyOnline.this, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue request= Volley.newRequestQueue(this);
        request.add(jsonObjectRequest);

    }

    private void showResponse(JSONObject jsonObject) {
        System.out.println(jsonObject);
        AlertDialog.Builder alertResp=new AlertDialog.Builder(this);
        alertResp.setTitle("Submitting Form..");
        alertResp.setMessage("Dear " + editName.getText().toString() + "\nThanks for submitting the form.Our team has recieved your form and will contact you soon.\n");
        alertResp.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertResp.create();
        alert.show();

    }

    public static class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        String dob;
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);

        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dob=""+dayOfMonth+"-"+monthOfYear+"-"+year;
            ApplyOnline.editTextDob.setText(dob);
        }
    }
    private boolean validateEmail(String email) {
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
