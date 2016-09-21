package in.ace.pardeep.org.acev2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener {

    EditText adminEmaill;
    EditText adminPassword;
    Button sigInAdminPage;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    private static boolean login=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        if(login){
            Intent intent=new Intent(AdminLogin.this,AdminManage.class);
            startActivity(intent);
        }

        adminEmaill=(EditText)findViewById(R.id.editTextUserNameAdmin);
        adminPassword=(EditText)findViewById(R.id.editTextPasswordAdmin);
        sigInAdminPage=(Button)findViewById(R.id.submitButtonAdminLogIn);
        sigInAdminPage.setOnClickListener(this);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayoutAdminLogin);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return true;
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_login, menu);
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
        switch (v.getId()){
            case R.id.submitButtonAdminLogIn:
                CheckValidUser();
                break;
        }
    }

    private void CheckValidUser() {
        if(adminEmaill.getText().toString().length()>0 && adminPassword.getText().toString().length()>0){
            if(checkValidEmail()){
                sendRequestToServer();
            }
            else {
                adminEmaill.setText("");
                Toast.makeText(getApplication(),"Please Enter Valid Email Id",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplication(),"Please fill the details",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRequestToServer() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setTitle("Log In");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,ScriptUrl.getDataManageer(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        checkResponse(s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("emailID",adminEmaill.getText().toString());
                param.put("password",adminPassword.getText().toString());
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checkResponse(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("server_response");
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
            String msg=jsonObject1.getString("message");
            if(msg.equalsIgnoreCase("User not exist")){
                progressDialog.dismiss();
                adminEmaill.setText("");
                adminPassword.setText("");
                Toast.makeText(getApplicationContext(),"Invalid User",Toast.LENGTH_SHORT).show();
            }
            else{
                progressDialog.dismiss();
                adminEmaill.setText("");
                adminPassword.setText("");
                login=true;
                Intent intent=new Intent(AdminLogin.this,AdminManage.class);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private boolean checkValidEmail() {
        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(adminEmaill.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isLogin() {
        return login;
    }

    public static void setLogin(boolean login) {
        AdminLogin.login = login;
    }
}
