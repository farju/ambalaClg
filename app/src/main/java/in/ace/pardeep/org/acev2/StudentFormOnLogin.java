package in.ace.pardeep.org.acev2;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFormOnLogin extends Fragment implements View.OnClickListener {

    CircleImageView circleImageView;
    Button button;
    ImageButton imageButton;
    EditText editText;
    View view;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableStr;
    private  static String imageName="";
    private static String sharePref="Profile";
    private static String imageAbsolutePath="";

    StudentProfile studentProfile;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    public StudentFormOnLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_student_form_on_login, container, false);
        circleImageView=(CircleImageView)view.findViewById(R.id.studentImage);
        button=(Button)view.findViewById(R.id.submitButton);
        imageButton=(ImageButton)view.findViewById(R.id.studentImageButtonSelect);
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
        editText=(EditText)view.findViewById(R.id.editTextUserName);
        linearLayout=(LinearLayout)view.findViewById(R.id.linearlayoutStudent);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard(v);
                return true;
            }
        });
        sharedPreferences=getActivity().getSharedPreferences(sharePref, Context.MODE_PRIVATE);
        if(sharedPreferences.contains("UserName")){
            studentProfile=new StudentProfile();
            studentProfile.setStudentName(sharedPreferences.getString("UserName",null));
            getFragmentManager().popBackStack();
            onDestroy();
        }

        return view;
    }

    private void hideKeyBoard(View v) {
        InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    @Override
    public void onClick(View v) {
        if(v==imageButton){
            Intent imageIntent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(imageIntent, RESULT_LOAD_IMG);
        }
        else if(v==button){
            progressDialog=new ProgressDialog(getActivity());

            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            storeData();

        }
    }

    private void storeData() {

        if(editText.getText().toString().length()<3 ){
            progressDialog.dismiss();
            Toast.makeText(getActivity(),"Please fill the name",Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.dismiss();
            saveData();
        }
    }

    private void saveData() {
        studentProfile=new StudentProfile();
        studentProfile.setStudentName(editText.getText().toString());
       // studentProfile.setRegistered(true);
        studentProfile.setRegistered();
        savedTosharedPreference();

        if(imageAbsolutePath.length()>0){
            studentProfile.setImagePath(imageAbsolutePath);
        }
        //onDestroy();
        onDestroy();
        int count=getFragmentManager().getBackStackEntryCount();
        if(count>0){
            getFragmentManager().popBackStack();
        }
    }

    private void savedTosharedPreference() {
      sharedPreferences=getActivity().getSharedPreferences(sharePref,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("UserName", editText.getText().toString());
        editor.commit();

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
              if(imageName.length()>0){
                   imageAbsolutePath= storeImage(BitmapFactory.decodeFile(imgDecodableStr));
                }
                System.out.println("Image Name........."+imageName);
                circleImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableStr));

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String storeImage(Bitmap bitmapImage) {
        ContextWrapper contextWrap=new ContextWrapper(getActivity());
        File directory=contextWrap.getDir("imageDir",Context.MODE_PRIVATE);
        File imagePath=new File(directory,StudentProfile.getProfileImage());
        FileOutputStream  fileOutput=null;

        try {
            fileOutput=new FileOutputStream(imagePath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG,100,fileOutput);

            System.out.println("Image Saved");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                fileOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
