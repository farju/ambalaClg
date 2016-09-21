package in.ace.pardeep.org.acev2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FacultyProfile extends AppCompatActivity {

    boolean handler;
    boolean doublePressToExit=false;
    private static boolean logout=false;


    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private static String[] listUpdate={"Upload Imp.Note/Ques./Any Text","Upload Assignments/Tut Sheets","Change Password","Students Query","Notifications"};

    ListView listView;

    public static boolean isLogout() {
        return logout;
    }

    public static void setLogout(boolean logout) {
        FacultyProfile.logout = logout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);
        Toolbar toolbar=(Toolbar)findViewById(R.id.tool1);
        ActionBar actionBar=getSupportActionBar();
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.listViewFaculty);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listUpdate);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        changeFragment(new NoteUpdateFacultyFrag());
                        break;
                    case 1:
                        changeFragment(new UploadAssignmentFragment());
                        break;
                    case 2:
                        changeFragment(new FacultyChangePassword());
                        break;
                    case 3:
                        showAlertDialog("Will be updated soon!");
                        break;
                    case 4:
                        showAlertDialog("Will be updated soon!");
                        break;
                    default:
                        showAlertDialog("Will be updated soon!");
                }
            }
        });


    }

    private void showAlertDialog(String s) {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Update");
        alertBuilder.setMessage(s);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertBuilder.create();
        alert.show();
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFacultyPortal,fragment,"fragment").setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("fragment").commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faculty_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.logout:
                logOutStudentPortal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void logOutStudentPortal() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Log Out");
        alert.setMessage("Do You Want Confirm to log Out ?");
        alert.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StudentPortal.saveReg=false;
                logout=true;
                deleteShareProfile();
                Intent intent = new Intent(getApplication(), FacultyPortal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }

    private void deleteShareProfile() {

        try {
            sharedPref = this.getSharedPreferences(FacultyPortal.getPrefFaculty(), 0);
            editor = sharedPref.edit();
            editor.remove("loginsuccess");
            editor.remove("facultyid");
            editor.remove("department");
            editor.remove("departmentId");
            editor.remove("email");
            editor.remove("facultyname");
            editor.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        int count=getFragmentManager().getBackStackEntryCount();
        if(count>0){
            getFragmentManager().popBackStack();
            return;
        }
        if(doublePressToExit) {
            super.onBackPressed();
            return;
        }
        this.doublePressToExit=true;
        Toast.makeText(getApplication(), "Press again To exit", Toast.LENGTH_SHORT).show();

        handler=new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublePressToExit=false;
            }
        },2000);

    }
}
