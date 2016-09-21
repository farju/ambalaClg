package in.ace.pardeep.org.acev2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
*ACE Android Application
* Develop by Pardeep (2313016) CSE Department
* Any query regarding Code or Version mail at pk.raswant@gmail.com
 */

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    //images
    public Integer images[]={R.drawable.ace_ic_event,R.drawable.ace_ic_bckg,R.drawable.ace_ic};
    private static Integer demoImages[]={R.drawable.first_screen,R.drawable.left_menu,R.drawable.right_menu};
    private static int demoImageCount;
    ImageView imageShow;
    Button imageButtonSlideRight;
    Button imageButtonSlideLeft;
    int custIndex=0;
    FrameLayout frameLayout;
    TextView textviewText,placementConnectionTextView,noticeConnectionTextView,eventsConnectionTextView;
    Button noticeButton,placementButton,eventsButton,placementButtonConnectionError,noticeButtonConnectionError;
    Button rightButtonOnMenu;
    Button refreshButtonActionBar;
    private static String[] imageUrls={};

    private static final String JSON_Url="https://api.myjson.com/bins/tdsf";
    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem aboutUs;
    private ResideMenuItem academic;
    private ResideMenuItem contactUs;
    private ResideMenuItem developer;
    private ResideMenuItem notices;
    private ResideMenuItem news;
    private ResideMenuItem events;
    private ResideMenuItem admissionsResideMenuItem;
    private ResideMenuItem placementResideMenuItem;
    private ResideMenuItem galleryResideMenuItem;
    private ResideMenuItem studentPortal;
    private ResideMenuItem facultyPortal;
    private Text text;
    TextView textView;
    private ListView listViewPlacement;
    private ListView listViewNotices;
    private ScrollView scrollView;
    PlacementListAdapter placementListAdapter;
    NoticesListAdapter noticesListAdapter;

   private SharedPreferences sharedPreferences;
   SharedPreferences.Editor edit;


    //shared preference for gcm
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";




    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    /**
     * Called when the activity is first created.
     */

    //

    //

    public HomeScreen() {

    }
    private  Context context=this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //List Buttons
        placementButton=(Button)findViewById(R.id.placementMoreButton);
        noticeButton=(Button)findViewById(R.id.noticeMoreButton);
        eventsButton=(Button)findViewById(R.id.eventsMoreButton);
        placementButton.setOnClickListener(this);
        noticeButton.setOnClickListener(this);
        eventsButton.setOnClickListener(this);

        mContext = this;
        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {

                } else {
                    // mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        //mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegisterationIntentService.class);
            startService(intent);
        }


        //sharedPreferences
       // sharedPreferences=this.getSharedPreferences("PlacementPref",Context.MODE_PRIVATE);




        System.out.println("Reg is started...........");
        setUpMenu();
        // ListView of Placement
        listViewPlacement=(ListView)findViewById(R.id.listViewPlacement);
        // this code disable the parent ScrollView when we use multiple ScrollView

        // code of ListView Of Placement
        listViewNotices=(ListView)findViewById(R.id.listViewNotices);
        sendRequest();
        sendRequestForNoticeList();



        /*
        *********Floating Button For apply*********
         */
        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingButtonHomeScreen);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Do you want to apply ACE");
                alertDialog.setMessage("Student Application Form\n Ambala College Of engineering \n& applied Research");
                alertDialog.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeScreen.this, ApplyOnline.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("DECLINE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });



        // Images
        System.out.println("size......................." + images.length);

        imageButtonSlideRight=(Button)findViewById(R.id.buttonImageHome);
        System.out.println("Hello......................");
        imageShow=(ImageView)findViewById(R.id.imageViewHomeScreen);
       // imageShow.setImageResource(images[custIndex]);
        imageShow.setVisibility(View.GONE);
        System.out.println(images[custIndex]);
        imageButtonSlideRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });
        imageButtonSlideRight.setVisibility(View.GONE);
        imageButtonSlideLeft=(Button)findViewById(R.id.imageButtonLeft);
        imageButtonSlideLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLeftImage();
            }
        });
        imageButtonSlideLeft.setVisibility(View.GONE);
        //list Connection views
        placementConnectionTextView=(TextView)findViewById(R.id.textViewPlacementListConnectionError);
        placementConnectionTextView.setVisibility(View.GONE);
        placementButtonConnectionError=(Button)findViewById(R.id.buttonPlacementListConnectionError);
        placementButtonConnectionError.setVisibility(View.GONE);
        eventsConnectionTextView=(TextView)findViewById(R.id.textViewEventsConnectionError);
        eventsConnectionTextView.setVisibility(View.GONE);
        placementButtonConnectionError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
                sendRequestForNoticeList();
            }
        });
        noticeConnectionTextView=(TextView)findViewById(R.id.textViewNoticeListConnectionError);
        noticeConnectionTextView.setVisibility(View.GONE);
        noticeButtonConnectionError=(Button)findViewById(R.id.buttonNoticeListConnectionError);
        noticeButtonConnectionError.setVisibility(View.GONE);
        noticeButtonConnectionError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendRequest();
                sendRequestForNoticeList();
            }
        });

        rightButtonOnMenu=(Button)findViewById(R.id.rightMenuButton);
        rightButtonOnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(HomeScreen.this,rightButtonOnMenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_home_screen,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.admin:
                                /*Intent adminLogin=new Intent(HomeScreen.this,AdminLogin.class);
                                startActivity(adminLogin);*/
                                showPopupDialog("For any Query you can directly contact to admin.\n You can send query at mail id:-\ndeveloper.aceapp@gmail.com");
                                return true;
                            case R.id.about:
                                showPopupDialog("This is beta version only for testing.Newer version have many updates and upload soon on play store\n." +
                                        "Testing period for aceapp is for 1 week and you can send your feedback any suggestion to developer Team. ");
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popupMenu.show();
            }
        });

        refreshButtonActionBar=(Button)findViewById(R.id.refreshButton);
        refreshButtonActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
                sendRequestForNoticeList();
                if(imageUrls.length<=0){
                    imageShow.setVisibility(View.GONE);
                    imageButtonSlideRight.setVisibility(View.GONE);
                    imageButtonSlideLeft.setVisibility(View.GONE);
                    loadEventImages();
                }
                Toast.makeText(getApplicationContext(),"Refresh",Toast.LENGTH_SHORT).show();
            }
        });

       /*
        Demo Guide
         */
        demoImageCount=0;
       sharedPreferences=this.getSharedPreferences("demoapp",0);
        if(!sharedPreferences.contains("firstview")){
           displayDialogDemoApp();
        }

        System.out.println(custIndex);
        System.out.println("Image url length :"+imageUrls.length);
        loadEventImages();

    }

    private void showPopupDialog(String s) {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Admin");
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

    private void loadEventImages() {

        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressEvents);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,ScriptUrl.getEventsGetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressBar.setVisibility(View.GONE);
                imageButtonSlideLeft.setVisibility(View.VISIBLE);
                imageButtonSlideRight.setVisibility(View.VISIBLE);
                imageShow.setVisibility(View.VISIBLE);
                serverResponseEventImages(s);

                if(imageUrls.length>0){
                    showImageEventUrls();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressBar.setVisibility(View.GONE);

                custIndex=0;
                imageShow.setVisibility(View.VISIBLE);
                imageButtonSlideLeft.setVisibility(View.VISIBLE);
                imageButtonSlideRight.setVisibility(View.VISIBLE);
                imageShow.setImageResource(images[custIndex]);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showImageEventUrls() {
        custIndex=0;
        if(!imageUrls[custIndex].equalsIgnoreCase("")) {
            Picasso.with(getApplicationContext()).load(imageUrls[custIndex]).noFade().into(imageShow);
        }
        else {
            Picasso.with(getApplicationContext()).load(R.drawable.picture).into(imageShow);
        }
        //custIndex++;
    }

    private void serverResponseEventImages(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("response");
            if(jsonArray.length()>0) {
                System.out.println(jsonArray.length());
                imageUrls=new String[jsonArray.length()];
                custIndex=jsonArray.length();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject childObject = jsonArray.getJSONObject(i);
                    String titleGet = childObject.getString("title");
                    String dateGet = childObject.getString("date");
                    String urlGet = childObject.getString("url");
                    if (urlGet.length()==0){
                        System.out.println("url null");
                        urlGet="";
                    }
                    String descriptionGet = childObject.getString("description");
                    System.out.println(titleGet);
                    System.out.println(dateGet);
                    System.out.println(urlGet);
                    System.out.println(descriptionGet);
                    imageUrls[i]=urlGet;
                }

            }
            else {
                Toast.makeText(this, "No Data To Display!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayDialogDemoApp() {
        final AlertDialog.Builder alertDemo=new AlertDialog.Builder(this);
        alertDemo.setTitle("App Guide");
        alertDemo.setCancelable(false);
        View view;
        LayoutInflater layoutInflater=this.getLayoutInflater();
        view=layoutInflater.inflate(R.layout.appguide,null);
        final ImageView imageView=(ImageView)view.findViewById(R.id.imageViewappdemo);
        imageView.setImageResource(demoImages[0]);
        Button skipDemo=(Button)view.findViewById(R.id.skipButton);
        final LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.bottomlayout);
        final CheckBox checkBox=(CheckBox)view.findViewById(R.id.checkboxViewDemo);
        final Button continueButton=(Button)view.findViewById(R.id.continueButton);
        checkBox.setVisibility(View.GONE);
        continueButton.setVisibility(View.GONE);

        Button button=(Button)view.findViewById(R.id.nextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoImageCount++;
                if (demoImageCount < demoImages.length) {
                    imageView.setImageResource(demoImages[demoImageCount]);
                } else {
                    /*imageView.setImageResource(demoImages[0]);
                    demoImageCount = 0;*/
                    linearLayout.setVisibility(View.GONE);
                    checkBox.setVisibility(View.VISIBLE);
                    continueButton.setVisibility(View.VISIBLE);
                }
            }
        });

        alertDemo.setView(view);
        final AlertDialog alertDemoApp=alertDemo.create();
        alertDemoApp.show();
        skipDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDemoApp.dismiss();
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    edit=sharedPreferences.edit();
                    edit.putBoolean("firstview",true);
                    edit.commit();
                    alertDemoApp.dismiss();
                }
                else {
                    alertDemoApp.dismiss();
                }
            }
        });

    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;

    }

    private void setLeftImage() {
        custIndex--;
        if(custIndex<0){
            custIndex=0;
        }

        showImage();
    }

    private void setInitialImage() {
        showImage();
    }

    private void setImage() {
        custIndex++;
        if(imageUrls.length>0 && custIndex==imageUrls.length){
            custIndex=0;
        }
        else if(custIndex==images.length){
            custIndex=0;
        }
        System.out.println(custIndex);

        showImage();
    }

    private void showImage() {
        imageShow=(ImageView)findViewById(R.id.imageViewHomeScreen);
        //imageShow.setImageResource(images[custIndex]);
        if(imageUrls.length>0) {
           // Picasso.with(getApplicationContext()).load(imageUrls[custIndex]).into(imageShow);
            if(!imageUrls[custIndex].equalsIgnoreCase("")) {
                Picasso.with(getApplicationContext()).load(imageUrls[custIndex]).noFade().into(imageShow);
            }
            else {
                Picasso.with(getApplicationContext()).load(R.drawable.picture).into(imageShow);
            }
        }
        else {
            imageShow.setImageResource(images[custIndex]);
        }


    }

    private void sendRequestForNoticeList() {
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressNotices);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.GET,ScriptUrl.getNoticeUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressBar.setVisibility(View.GONE);
                noticeButtonConnectionError.setVisibility(View.GONE);
                noticeConnectionTextView.setVisibility(View.GONE);
                saveDataToNoticeFile(s);
                showResponseNoticeList(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Toast.makeText(context, "Network Problem", Toast.LENGTH_SHORT).show();
                String result=getDataFromNoticeFile();
                if(result!=null){
                    progressBar.setVisibility(View.GONE);
                    showResponseNoticeList(result);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    noticeButtonConnectionError.setVisibility(View.VISIBLE);
                    noticeConnectionTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private String getDataFromNoticeFile() {
        String read=null;
        try {
            String readData;
            InputStream inputStream=openFileInput(Files.fileNotice);
            if(inputStream!=null){
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder=new StringBuilder();
                while((readData=bufferedReader.readLine())!=null){
                    stringBuilder.append(readData+"\n");
                    read=readData;
                }
            }

        }
        catch (java.io.FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return read;

    }

    private void saveDataToNoticeFile(String s) {
        try{
            File file=new File(context.getFilesDir(),Files.fileNotice);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(openFileOutput(Files.fileNotice,0));
            outputStreamWriter.write(s.toString());
            outputStreamWriter.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getPlacementSharedPreferences() {



    }



    private void showResponseNoticeList(String s) {
        if(s.equalsIgnoreCase("Network Error")){
            Toast.makeText(HomeScreen.this, "Server Under Working Try After Sometime!", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            ParseJsonListNotices parse = new ParseJsonListNotices(s);
            parse.parseJSON();

            NoticesListAdapter notice = new NoticesListAdapter(ParseJsonListNotices.description, ParseJsonListNotices.date);
            listViewNotices.setAdapter(notice);
            listViewNotices.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            view.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                    view.onTouchEvent(motionEvent);
                    return true;
                }
            });
        }

    }

    private void sendRequest() {
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressPlacement);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(ScriptUrl.placementUrl, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                listViewNotices.setVisibility(View.VISIBLE);
                placementButtonConnectionError.setVisibility(View.GONE);
                placementConnectionTextView.setVisibility(View.GONE);
                System.out.println("Response ......................................");
                saveDataToFile(response);
                showResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressBar.setVisibility(View.GONE);
               // PlacementFragment placementFragment=new PlacementFragment();
                String result=getDataFromFile();
                if (result!=null) {
                    showResponse(result);

                } else {
                   /* final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Connection Error");
                    alertDialog.setMessage("Connection Not Found.For Refresh Press Ok");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sendRequest();
                            dialogInterface.cancel();
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alert = alertDialog.create();
                    alert.show();*/
                    placementButtonConnectionError.setVisibility(View.VISIBLE);
                    placementConnectionTextView.setVisibility(View.VISIBLE);

                }
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void saveDataToFile(String response) {
        try {
            File file = new File(context.getFilesDir(), Files.fileName);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(openFileOutput(Files.fileName, 0));
            //JSONObject jsonObject=new JSONObject(s);
            String update=response.toString();
            System.out.println(update);
            outputStreamWriter.write(response.toString());
            outputStreamWriter.close();
            System.out.println("File save.....");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("File not save.....");
        }
    }

    public String getDataFromFile() {
        String readData=null;
        try {
            String res = null;
            InputStream inputStream = openFileInput(Files.fileName);
            System.out.println("file read");
            if(inputStream!=null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                System.out.println("file read");
                StringBuilder stringBuilder=new StringBuilder();
                while ((res=bufferedReader.readLine())!=null){
                    stringBuilder.append(res + "\n");
                    System.out.println(res);
                    readData=res;
                }
                inputStream.close();

            }
        }
        catch (java.io.FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return readData;
    }

    private void showResponse(String response) {
        if(response.equalsIgnoreCase("Network Error")){
            Toast.makeText(HomeScreen.this, "Server Under Working Try After Sometime!", Toast.LENGTH_SHORT).show();
            return;
        }
        ParseJSONList parseJSONList=new ParseJSONList(response);
        parseJSONList.parseJSON();
        PlacementListAdapter placementListAdapter=new PlacementListAdapter(ParseJSONList.description,ParseJSONList.date);
        listViewPlacement.setAdapter(placementListAdapter);

        listViewPlacement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                view.onTouchEvent(motionEvent);
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    public void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.ace_ic_ace);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.5f);
        textView=new TextView(this.mContext);
        // create menu items;
        String str="About Us";
        aboutUs     = new ResideMenuItem(this, R.drawable.ic_about,str);
        resideMenu.addMenuItem(aboutUs, ResideMenu.DIRECTION_LEFT);
        academic= new ResideMenuItem(this, R.drawable.ic_acad,"Academic");
        resideMenu.addMenuItem(academic, ResideMenu.DIRECTION_LEFT);
        admissionsResideMenuItem=new ResideMenuItem(this,R.drawable.student_id,"Admission");
        resideMenu.addMenuItem(admissionsResideMenuItem,ResideMenu.DIRECTION_LEFT);
        placementResideMenuItem=new ResideMenuItem(this,R.drawable.ic_job,"Placement");
        resideMenu.addMenuItem(placementResideMenuItem,ResideMenu.DIRECTION_LEFT);
        contactUs = new ResideMenuItem(this, R.drawable.ic_cont,"ContactUs");
        resideMenu.addMenuItem(contactUs, ResideMenu.DIRECTION_LEFT);
        galleryResideMenuItem=new ResideMenuItem(this,R.drawable.ic_gallery,"Gallery");
        resideMenu.addMenuItem(galleryResideMenuItem,ResideMenu.DIRECTION_LEFT);
        notices = new ResideMenuItem(this, R.drawable.ic_notice, "Notices");
        resideMenu.addMenuItem(notices, ResideMenu.DIRECTION_RIGHT);
      //  news = new ResideMenuItem(this, R.drawable.ic_news, "News");
        resideMenu.setShadowVisible(true);
        //resideMenu.addMenuItem(news, ResideMenu.DIRECTION_RIGHT);
        events = new ResideMenuItem(this, R.drawable.ic_events, "Events");
        resideMenu.addMenuItem(events, ResideMenu.DIRECTION_RIGHT);
        studentPortal=new ResideMenuItem(this,R.drawable.ic_studentportal,"Student\nPortal");
        resideMenu.addMenuItem(studentPortal,ResideMenu.DIRECTION_RIGHT);
        facultyPortal=new ResideMenuItem(this,R.drawable.ic_faculty,"Faculty");
        resideMenu.addMenuItem(facultyPortal,ResideMenu.DIRECTION_RIGHT);
        developer = new ResideMenuItem(this, R.drawable.ic_manager, "Developer");
        resideMenu.addMenuItem(developer, ResideMenu.DIRECTION_RIGHT);

        /*aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, AboutUs.class);
                startActivity(intent);
                resideMenu.closeMenu();
            }
        });*/
        aboutUs.setOnClickListener(this);
        academic.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        developer.setOnClickListener(this);
        notices.setOnClickListener(this);
      //  news.setOnClickListener(this);
        events.setOnClickListener(this);
        admissionsResideMenuItem.setOnClickListener(this);
        placementResideMenuItem.setOnClickListener(this);
        galleryResideMenuItem.setOnClickListener(this);
        studentPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeScreen.this,StudentPortal.class);
                startActivity(intent);
                resideMenu.closeMenu();
            }
        });
        facultyPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeScreen.this,FacultyPortal.class);
                startActivity(intent);
                resideMenu.closeMenu();
            }
        });
        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        findViewById(R.id.action_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == academic){


            changeFragment(new Academic());
        } else if (view == aboutUs){
            changeFragment(new AboutUsFragment());
        } else if (view == admissionsResideMenuItem){
            changeFragment(new AdmissionFragment());
        }else if (view == placementResideMenuItem || view==placementButton){
            changeFragment(new PlacementFragment());
        }else if(view==galleryResideMenuItem){
            //changeFragment(new GalleryFragment());
           // buildAlertUpdateSoon("Gallery");
            startActivity(new Intent(HomeScreen.this,GalleryAceActivity.class));
        }else if(view==notices ||view==noticeButton){
            changeFragment(new NoticesFragment());
        }/*else if(view==news){
           // changeFragment(new NewsFragment());
            buildAlertUpdateSoon("News");
        }*/else if(view==events ||view==eventsButton){
            changeFragment(new EventsFragment());
        }else if(view==developer){
           // changeFragment(new DeveloperFragment());
            startActivity(new Intent(HomeScreen.this,Developers.class));
        }else if(view==contactUs){
            changeFragment(new ContactUsFragment());
        }


        resideMenu.closeMenu();
    }

    private void buildAlertUpdateSoon(String title) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(title + " will be update soon!");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();

    }

    private void changeFragment(Fragment fragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,fragment,"homescreen").
                setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("homescreen").commit();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        }
        else{
            getFragmentManager().popBackStack();
        }

        String sdState = android.os.Environment.getExternalStorageState();
        try {
            File dir = new File(Environment.getExternalStorageDirectory()+"/data/aceapp");
            if(dir.exists()) {
                if (dir.isDirectory()) {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++) {
                        System.out.println("Delete file");
                        new File(dir, children[i]).delete();
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(mContext,"Welcome!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
        }
    };




    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

}

