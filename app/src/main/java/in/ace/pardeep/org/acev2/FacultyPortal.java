package in.ace.pardeep.org.acev2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class FacultyPortal extends AppCompatActivity implements View.OnClickListener {

    EditText username,password;
    Button signInButton,forgetButton,signUpButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static String prefFaculty="profilefac";

    public static String getPrefFaculty() {
        return prefFaculty;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_portal);
        username=(EditText)findViewById(R.id.editTextUserNameFacultyPortal);
        password=(EditText)findViewById(R.id.editTextPasswordFacultyPortal);

        signInButton=(Button)findViewById(R.id.submitButtonFacultyPortal);
        //forgetButton=(Button)findViewById(R.id.forgotButtonFacultyPortal);
        signUpButton=(Button)findViewById(R.id.buttonsignupfaculty);

        signInButton.setOnClickListener(this);
       // forgetButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

        sharedPreferences=this.getSharedPreferences(prefFaculty,0);
        if(sharedPreferences.getBoolean("loginsuccess",false)){
            startActivity(new Intent(FacultyPortal.this,FacultyProfile.class));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faculty_portal, menu);
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
        if(v==signInButton){
            verifyDetails();
        }
        else if(v==signUpButton){
            changeFragment(new FacultySignUp());
        }
    }
    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameFacultyPortal,fragment,"fragment").setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("fragment").commit();
    }

    private void verifyDetails() {
        if(username.getText().toString().length()>0 && password.getText().toString().length()>0){
            if(validateEmail(username.getText().toString())){
                String email=username.getText().toString();
                String passwordLogin=password.getText().toString();
                sendRequestForLogin(email,passwordLogin);
            }
            else {
                Toast.makeText(FacultyPortal.this, "Email Address is not Correct!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(FacultyPortal.this, "Please fill the required Details!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRequestForLogin(String email, final String passwordLogin) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        HashMap<String,String>  hashMap=new HashMap<String, String>();
        hashMap.put("email",email);
        hashMap.put("password",passwordLogin);

        JSONObject jsonObject=new JSONObject(hashMap);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://aceapp-pardeep16.rhcloud.com/faculty/api/login",jsonObject, new Response.Listener<JSONObject>() {
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
                Toast.makeText(FacultyPortal.this, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue request= Volley.newRequestQueue(this);
        request.add(jsonObjectRequest);
    }

    private void saveToSharePreference(JSONObject jsonObject) {
        sharedPreferences=this.getSharedPreferences(prefFaculty,0);
        editor=sharedPreferences.edit();
        try {
            editor.putBoolean("loginsuccess",true);
            editor.putString("facultyid", jsonObject.getString("id").toString());
            editor.putString("department",jsonObject.getString("departmentname").toString());
            editor.putString("departmentId",jsonObject.getString("departmentId").toString());
            editor.putString("email",jsonObject.getString("email").toString());
            editor.putString("facultyname",jsonObject.getString("facultyName").toString());
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void buildDialog(String s, String msg, final boolean flag) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle(s);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(flag){
                    startActivity(new Intent(FacultyPortal.this,FacultyProfile.class));
                }
                else {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    private void showResponse(JSONObject jsonObject) {
        try {
            JSONObject object=jsonObject.getJSONObject("result");
            boolean flag=object.getBoolean("success");
            if(flag){
                saveToSharePreference(object);
                buildDialog("Login Success!",object.getString("msg").toString(),flag);
            }
            else {
                buildDialog("Login Failed!",object.getString("msg").toString(),flag);
            }

        } catch (JSONException e) {
            e.printStackTrace();
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

    @Override
    public void onBackPressed() {
        if(FacultyProfile.isLogout()){
            FacultyProfile.setLogout(false);
            System.out.println(FacultyProfile.isLogout());
            gotoMainActivity();
        }
        super.onBackPressed();
    }

    private void gotoMainActivity() {
        Intent intent=new Intent(FacultyPortal.this,HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
