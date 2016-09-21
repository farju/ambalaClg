package in.ace.pardeep.org.acev2;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentProfileFragment extends Fragment {

    Button button;
    View view;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static String imagePref="image";

    TextView textView,profileTextView;


    public StudentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_student_profile, container, false);
        button=(Button)view.findViewById(R.id.backButtonProfile);
        textView=(TextView)view.findViewById(R.id.studentName);
        profileTextView=(TextView)view.findViewById(R.id.textViewUserProfile);
        loadStudentProfileData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                onDestroy();
            }
        });

       /* sharedPreferences=getActivity().getSharedPreferences(imagePref, Context.MODE_PRIVATE);
        if(StudentProfile.getImagePathInFiles().length() >0){
            loadImage();
            saveToSharedPrefrence();
        }
        else if(sharedPreferences.contains("imagepath")){
            loadImagePathFromSharedPrefrence();
        }*/

        return view;
    }

    private void loadStudentProfileData() {
        sharedPreferences=getActivity().getSharedPreferences(StudentPortal.getSharedPrefProfile(),0);

        if(sharedPreferences.getBoolean("profile",false)) {
            String rollno = sharedPreferences.getString("rollno", null);
            String name = sharedPreferences.getString("name", null);
            String email = sharedPreferences.getString("email", null);
            String department = sharedPreferences.getString("department", null);
            String semester = sharedPreferences.getString("semester", null);
            String coursetaken = sharedPreferences.getString("course", null);
            String deptname=sharedPreferences.getString("deptname",null);

            String text = "Student Name :" + name + "\n\n" + "Roll No :" + rollno + "\n\n" + "Current Semester :" + semester + "\n\n" + "Course Taken :" + coursetaken + "\n\n"+"Department :"+ deptname +"\n\n";


            if (textView != null) {
                textView.setText(name);
            }
            if (profileTextView != null) {
                profileTextView.setText(text);
            }
        }

        /*editor.putString("rollno",rollno);
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("department",dept);
        editor.putString("course",course);
        editor.putString("semester",semester);*/

    }

   /* private void loadImagePathFromSharedPrefrence() {
        sharedPreferences=getActivity().getSharedPreferences(imagePref,Context.MODE_PRIVATE);
        try {
            File file=new File(sharedPreferences.getString("imagepath",null),StudentProfile.getProfileImage());
            Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(file));
            CircleImageView circleImage=(CircleImageView)view.findViewById(R.id.studentImage);
            circleImage.setImageBitmap(bitmap);

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
*/


   /* private void saveToSharedPrefrence() {
        sharedPreferences=getActivity().getSharedPreferences(imagePref,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("imagepath",StudentProfile.getImagePathInFiles().toString());
        editor.commit();
        System.out.println("Image save in shared prefrence");
    }
*/
    public void loadImage(){
        try {
            File file=new File(StudentProfile.getImagePathInFiles(),StudentProfile.getProfileImage());
            Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(file));
            CircleImageView circleImage=(CircleImageView)view.findViewById(R.id.studentImage);
            circleImage.setImageBitmap(bitmap);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

}
