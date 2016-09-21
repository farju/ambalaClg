package in.ace.pardeep.org.acev2;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentLoginFragment extends Fragment implements View.OnClickListener {

    ImageView imageView;
    Button imageSelectButton;
    Button submit;
    EditText userName;
    EditText emailId;
    Spinner spinner;
    private String departmentSelect;
    private TextView textView;
    private static String[] department={"Select","Computer Science and Engineering","Mechanical Engineering","Electronics and Communication","Biotechnology"};

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableStr;
    private String name;
    private String email;
    private String imageName=null;
    LinearLayout linearLayout;
    private View view;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static String sharedPrefName="StudentDetails";
    StudentProfile studentProfile;
    public StudentLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_student_login, container, false);
            imageSelectButton = (Button) view.findViewById(R.id.imageSelectUser);
            userName = (EditText) view.findViewById(R.id.userProfileName);
            emailId = (EditText) view.findViewById(R.id.emailProfile);
            spinner = (Spinner) view.findViewById(R.id.spinnerUserProfile);
            submit = (Button) view.findViewById(R.id.submitForm);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, department);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    departmentSelect = department[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            name = userName.getText().toString();
            email = emailId.getText().toString();
            submit.setOnClickListener(this);

            imageSelectButton.setOnClickListener(this);

            linearLayout = (LinearLayout) view.findViewById(R.id.linearlayoutStudentLogin);
            linearLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(v);
                    return true;
                }
            });

            // displayStudentProfileForm();
        return view;
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    private void displayStudentProfileForm() {

    }



    @Override
    public void onClick(View v) {
        if(v==imageSelectButton){
            Intent imageIntent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(imageIntent,RESULT_LOAD_IMG);
        }
        else if(v==submit){
            submitDetails();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG  && null != data) {
                // Get the Image from data
                System.out.println("Hello");
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableStr = cursor.getString(columnIndex);
                System.out.println("image ...." + imgDecodableStr);
                cursor.close();
               // imageSelectButton.setVisibility(View.GONE);
                // Set the Image in ImageView after decoding the String
                File file=new File(imgDecodableStr);
                imageName=file.getName();

                if(imgDecodableStr.length()>0){
                    textView=(TextView)view.findViewById(R.id.selectImageText);
                    textView.setVisibility(View.GONE);
                }
                System.out.println("Image Name........."+imageName);
               imageView=(ImageView)view.findViewById(R.id.studentImageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableStr));

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void submitDetails() {
        if(userName.getText().toString().length()>0 && emailId.getText().toString().length()>0 && departmentSelect.length()>0){
           if(departmentSelect.equalsIgnoreCase("Select")){
               Toast.makeText(getActivity(),"Please choose department",Toast.LENGTH_SHORT).show();
           }

            else {
               storeData();
           }

        }
        else{
            Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
        }
    }

    private void storeData() {

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setTitle("Student Portal");
        progressDialog.setMessage("Please Wait");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ScriptUrl.getStudentDataBaseUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                System.out.println(s);
                showResponse(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                Toast.makeText(getActivity(),"Network Error Try Again!",Toast.LENGTH_SHORT).show();
                textView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.userprof);
                userName.setText("");
                emailId.setText("");
                spinner.setSelection(0);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                    param.put("StudentName",userName.getText().toString());
                    param.put("StudentEmail",emailId.getText().toString());
                param.put("Department",departmentSelect.toString());
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showResponse(String s) {
        try {
            if(s.equalsIgnoreCase("Data Saved")){
                sharedPreferences=getActivity().getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.putBoolean("SubmitForm", true);
                editor.commit();
               // studentProfile=new StudentProfile();
                onDestroy();
            }
            else if(s.equalsIgnoreCase("Already exist")){
                Toast.makeText(getActivity(),"Email alredy exist",Toast.LENGTH_SHORT).show();
                //textView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.userprof);
                userName.setText("");
                emailId.setText("");
                spinner.setSelection(0);
            }
            else{
                Toast.makeText(getActivity(),"Error ! Please Try Again",Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.userprof);
                userName.setText("");
                emailId.setText("");
                spinner.setSelection(0);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void storeImage() {

    }


    @Override
    public void onDestroy() {
        int count=getFragmentManager().getBackStackEntryCount();

        if(count==0){
            super.onDestroy();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
}
