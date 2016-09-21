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
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class StudentProfile extends AppCompatActivity {
    private static boolean flag=false;
    private static String studentName="";
    private static String studentDepartment="";
    private static String profileImage="profileImage.jpg";
    private static  String imagePathInFiles="";
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static String sharePrefProfile="EditProfilePage";
    private static boolean logout=false;

    private static String[] listoptionsDownload={"Download Imp.Note/Ques.","Download Assignments/Tut Sheet"};

    LinearLayout linearLayout;

    StudentPortal studentPortal;
    private Button rightMenu;

    boolean handler;
    boolean doublePressToExit=false;
    ProgressBar progressBar;

    GridView gridView;


  //  DrawerLayout drawerLayout;
   // NavigationView navigationView;


   // Button drawerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardstudentportal);
        sharedPreferences=getApplicationContext().getSharedPreferences(sharePrefProfile, MODE_PRIVATE);

        Toolbar toolbar=(Toolbar)findViewById(R.id.tool1);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();


     //  progressBar=(ProgressBar)findViewById(R.id.progressBarStudentProfile);
       // progressBar.setVisibility(View.VISIBLE);
        //StudentLoginFragment studentLoginFragment=new StudentLoginFragment();
       // changeFragment(new StudentFormOnLogin());
       // progressBar.setVisibility(View.GONE);
        if(getStudentName().length()>0){
            savedTosharedPreference(getStudentName());
        }

        /*rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(StudentProfile.this,rightMenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_student_profile,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                logOutStudentPortal();

                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Under Working !", Toast.LENGTH_SHORT).show();

                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        drawerButton=(Button)findViewById(R.id.buttonDrawer);
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("button pressed");

            }
        });*/


       /* drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
               // Toast.makeText(getApplicationContext(),"Item selected",Toast.LENGTH_SHORT).show();
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        changeFragment(new StudentProfileFragment());
                        return true;
                    default:
                        return false;
                }

            }
        });
            */


        /*
        **
         */
        gridView=(GridView)findViewById(R.id.gridViewStudentDashboard);
        final DashboardAdapter dashboardAdapter=new DashboardAdapter();
        gridView.setAdapter(dashboardAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        changeFragment(new StudentProfileFragment());
                        break;
                    case 1:
                        showUploadDialog();
                        break;
                    case 2:
                        showOptionsToSelect();
                        break;
                    case 3:
                        showAlertDialog("Records will be updated soon!","Records");
                        break;
                    case 4:
                        showAlertDialog("Notifications facilties will be updated soon!","Notifications");
                        break;
                    case 5:
                        changeFragment(new ChangePassword());
                        break;
                }
            }
        });
    }

    private void showAlertDialog(String s, String records) {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle(records);
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

    private void showUploadDialog() {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Upload");
        alertBuilder.setMessage("Sorry! Upload doc/resume will be updated soon!");
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertBuilder.create();
        alert.show();
    }

    private void showOptionsToSelect() {
        AlertDialog.Builder alertDialogShow=new AlertDialog.Builder(this);
        alertDialogShow.setTitle("Select to Download");
        alertDialogShow.setItems(listoptionsDownload, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        changeFragment(new DownloadsStudentPortal());
                        break;
                    case 1:
                        changeFragment(new DownloadAssignments());
                        break;
                }
            }
        });
        alertDialogShow.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialogShow.create();
        alert.show();
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
                Intent intent = new Intent(getApplication(), StudentPortal.class);
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
        sharedPreferences=this.getSharedPreferences(StudentPortal.getSharedPrefProfile(), 0);
        editor=sharedPreferences.edit();
        editor.putBoolean("profile",false);
        editor.remove("rollno");
        editor.remove("name");
        editor.remove("email");
        editor.remove("department");
        editor.remove("course");
        editor.remove("semester");
        editor.remove("deptname");
        editor.commit();
    }

    public void  setRegistered(){
        //flag=value;
        System.out.println("name is...." + getStudentName());
    }

    public void savedTosharedPreference(String s) {

            sharedPreferences = getApplication().getSharedPreferences(sharePrefProfile,0);
            editor = sharedPreferences.edit();
            editor.putBoolean("Updated", true);
            editor.putString("Name", s);
            System.out.println("Hello");
            editor.commit();
      System.out.println("sharedpref..........." + sharedPreferences.getBoolean("Updated", false));
        System.out.println("name in shared pref is........." + sharedPreferences.getString("Name", ""));
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.studentHomeFragment,fragment,"fragment").setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("fragment").commit();
    }

    public  String getStudentDepartment() {
        return studentDepartment;
    }

    public  void setStudentDepartment(String studentDepartment) {
        StudentProfile.studentDepartment = studentDepartment;
    }

    public  String getStudentName() {
        return studentName;
    }

    public  void setStudentName(String studentName) {
        StudentProfile.studentName = studentName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_profile, menu);
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
        /*   case android.R.id.home:
               drawerLayout.openDrawer(GravityCompat.START);
               return true;*/
           case R.id.logout:
               logOutStudentPortal();
               return true;

           default:
               return super.onOptionsItemSelected(item);
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
        Toast.makeText(getApplication(),"Press again To exit",Toast.LENGTH_SHORT).show();

        handler=new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublePressToExit=false;
            }
        },2000);

    }

    public static String getImagePathInFiles() {
        return imagePathInFiles;
    }

    public void setImagePath(String path){
        imagePathInFiles=path;
    }

    public static String getProfileImage() {
        return profileImage;
    }

    public static void setProfileImage(String profileImage) {
        StudentProfile.profileImage = profileImage;
    }

    public static boolean isLogout() {
        return logout;
    }

    public static void setLogout(boolean logout) {
        StudentProfile.logout = logout;
    }
}
