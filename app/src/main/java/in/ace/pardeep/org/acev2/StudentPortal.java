package in.ace.pardeep.org.acev2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class StudentPortal extends AppCompatActivity {

    EditText editText;
    EditText editText1;
    LinearLayout linearLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static String sharePreferenceLogin="StudentPortalLogin";

    private static String  loginrollno=null;
    private static String loginpassword=null;

    public static boolean saveReg=true;
    private static String sharedPrefProfile="profiledata";

    public static String getSharedPrefProfile() {
        return sharedPrefProfile;
    }

    Button button;
    Button registerButton,forgotButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_portal);

        if(!saveReg){
            deleteSharedPreference();
        }

        sharedPreferences=getApplicationContext().getSharedPreferences(sharePreferenceLogin,MODE_PRIVATE);
        if(sharedPreferences.contains("Login")){
            if(sharedPreferences.getBoolean("Login",true)){
                Intent intent=new Intent(this,StudentProfile.class);
                startActivity(intent);
            }
        }
        //
        editText=(EditText)findViewById(R.id.editTextUserNameStudentPortal);
        editText1=(EditText)findViewById(R.id.editTextPasswordStudentPortal);
        button=(Button)findViewById(R.id.submitButtonStudentPortal);
        registerButton=(Button)findViewById(R.id.buttonRegisterStudentPortal);
        forgotButton=(Button)findViewById(R.id.forgotButtonStudentPortal);
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ForgotPassword());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new StudentSignUp());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() > 0 && editText1.getText().toString().length() > 0) {
                    loginUser();
                } else {
                    Toast.makeText(getBaseContext(), "Please fill all the fields ", Toast.LENGTH_LONG).show();
                }
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
        linearLayout=(LinearLayout)findViewById(R.id.linearLayoutStudentPortal);
       linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return true;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameStudentPortal,fragment,"fragment").setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("fragment").commit();
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void loginUser() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        loginrollno=editText.getText().toString();
        loginpassword=editText1.getText().toString();
        String url="http://xdeveloper.royalwebhosting.net/login.php";

       // final String obj=new Gson().toJson(hashMap);

        /*StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
                   }
        }) {


        };
        */

        HashMap<String,String> hashMap=new HashMap<String, String>();
        hashMap.put("loginrollno", editText.getText().toString());
        hashMap.put("loginpassword", editText1.getText().toString());
        JSONObject jsonObject=new JSONObject(hashMap);


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://aceapp-pardeep16.rhcloud.com/student/api/login",jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                System.out.println("Response.................." + jsonObject);
                showResponse(jsonObject);
                saveToSharePreference(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(StudentPortal.this, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue request= Volley.newRequestQueue(this);
        request.add(jsonObjectRequest);

    }

    private void saveToSharePreference(JSONObject jsonObject) {
        sharedPreferences=this.getSharedPreferences(sharedPrefProfile, 0);
        editor=sharedPreferences.edit();
        try {

            JSONArray jsonArray=jsonObject.getJSONArray("server_response");
            int size=jsonArray.length();
            JSONObject childObject=jsonArray.getJSONObject(0);
           String rollno=childObject.getString("rollno").toString();
            String name=childObject.getString("name");
            String email=childObject.getString("email");
            String dept=childObject.getString("dept");
            String course=childObject.getString("course");
            String semester=childObject.getString("semester");
            String deptname=childObject.getString("deptname");
            editor.putBoolean("profile",true);
            editor.putString("rollno", rollno);
            editor.putString("name",name);
            editor.putString("email",email);
            editor.putString("department",dept);
            editor.putString("course",course);
            editor.putString("semester",semester);
            editor.putString("deptname",deptname);
            editor.commit();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showResponse(JSONObject jsonObject) {

        try {

            JSONArray jsonArray=jsonObject.getJSONArray("server_response");
            int size=jsonArray.length();
            JSONObject childObject=jsonArray.getJSONObject(0);
            String title=childObject.getString("body");
            //
            System.out.println("body......"+title);

            String msg=childObject.getString("message");

            //
            System.out.println("message............"+msg);
            buildDialog(title, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void buildDialog(final String title, String msg) {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(StudentPortal.this, "Thanks for using services", Toast.LENGTH_SHORT).show();
                if(title.equalsIgnoreCase("Login Success")){
                    updateDataBase();
                    loginActivity();
                }
            }
        });
        AlertDialog dialog=alert.create();
        dialog.show();

        editText.setText("");
        editText1.setText("");
    }

    private void updateDataBase() {

        saveReg=true;
        sharedPreferences=getApplicationContext().getSharedPreferences(sharePreferenceLogin,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("Login", true);
        editor.putString("rollNo", loginrollno);
        editor.putString("password", loginpassword);
        editor.commit();

        System.out.println("rollno in shared pref............" + sharedPreferences.getString("rollNo", null));
        System.out.println("password in shared pref..........." + sharedPreferences.getString("password", null));
    }

    private void loginActivity() {
        Intent intent=new Intent(this,StudentProfile.class);
        startActivity(intent);
    }
    public void deleteSharedPreference(){
        sharedPreferences=getApplicationContext().getSharedPreferences(sharePreferenceLogin,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.remove("Login");
        editor.remove("rollNo");
        editor.remove("password");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        System.out.println("Back press");
        if(StudentProfile.isLogout()){
            StudentProfile.setLogout(false);
            System.out.println(StudentProfile.isLogout());
            gotoMainActivity();
        }
        super.onBackPressed();
    }

    private void gotoMainActivity() {
        Intent intent=new Intent(StudentPortal.this,HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
