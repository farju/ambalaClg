package in.ace.pardeep.org.acev2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 created by pardeep cse 
 */
public class Academic extends android.support.v4.app.Fragment implements View.OnClickListener {




   ListView listView;
   private String[] academicList={"Courses","Departments","Library","Academic Calendar","Syllabus","Time Table"};
    private static String[] departmentList={"Applied Science & Humanities","Biotechnology Engineering","Computer Science & Engineering","Electronics and Communication Engineering","Mechanical Enginnering"};

    private static String[] departmentListSyllabus={"Select department..","Biotechnology Engineering","Computer Science & Engineering","Electronics and Communication Engineering","Mechanical Enginnering"};
    private static String[] semesterList={"Select Semester..","1st","2nd","3rd","4th","5th","6th","7th","8th"};

    private static String[] deptIdTag={null,"biotech","cse","ece","mech"};

    private View view;
    private ResideMenu resideMenu;
    Button downloadButton;

    DownloadCalender downloadCalender=null;
    DownloadImage downloadImage=null;


    public Academic() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_academic, container, false);
      //  setUpView();
       listView=(ListView)view.findViewById(R.id.listAcademic);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,academicList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getActivity(), CoursesOffered.class);
                        startActivity(intent);

                        break;
                    case 1:
                        showDepartments();
                        break;
                    case 2:
                        showLibraryFrag();
                        break;
                    case 3:
                        showAcademicCalender();
                        break;
                    case 4:
                        showSyllabus();
                        break;
                    case 5:
                        showTimeTable();
                        break;
                }
            }
        });


        return view;
    }

    private void showTimeTable() {
         String[] departments={"Select Department","Applied Science & Humanities","Biotechnology Engineering","Computer Science & Engineering","Electronics and Communication Engineering","Mechanical Enginnering"};

        final String[] chooseDept = {""};
        AlertDialog.Builder timetable=new AlertDialog.Builder(getActivity());
        timetable.setMessage("Select Department");

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.timetable_layout,null);
        timetable.setView(view);

        final Spinner selectDept=(Spinner)view.findViewById(R.id.spinnerSelectDepartment);


        ArrayAdapter<String> arrayAdaptTimeTable=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,departments);
        selectDept.setAdapter(arrayAdaptTimeTable);

        Button button=(Button)view.findViewById(R.id.buttonDownload);


        selectDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        chooseDept[0] = "";
                        break;
                    case 1:
                        chooseDept[0] = "applied";
                        break;
                    case 2:
                        chooseDept[0] = "biotech";
                        break;
                    case 3:
                        chooseDept[0] = "cse";
                        break;
                    case 4:
                        chooseDept[0] = "ece";
                        break;
                    case 5:
                        chooseDept[0] = "mech";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseDept[0].equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select your department!", Toast.LENGTH_SHORT).show();
                } else {
                    sendRequestForTimeTable(chooseDept[0]);
                }
            }
        });

        timetable.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=timetable.create();
        alert.show();
    }

    private void sendRequestForTimeTable(final String msg) {
        final ProgressDialog progress=new ProgressDialog(getActivity());
        progress.setMessage("Please Wait..");
        progress.show();
        progress.setCancelable(false);
        System.out.println("request");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://aceapp-pardeep16.rhcloud.com/academic/timetable", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.dismiss();
                serverResonse(s);
                System.out.println(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.dismiss();
                Toast.makeText(getActivity(), "Error!Try Again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<>();
                param.put("branch",msg);
                return param;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void serverResonse(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONObject childOject=jsonObject.getJSONObject("result");

            if(childOject.getString("url").length()>0){
                String message=childOject.getString("message").toString();
                String url=childOject.getString("url").toString();
                downloadImage=new DownloadImage(url,message);
                downloadImage.execute();
            }
            else {

                    showErrorDialog("Not Found!");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void timeTableView(String message, final Bitmap bitmap) {
        if(bitmap==null){
            AlertDialog.Builder alertDialogError = new AlertDialog.Builder(getActivity());
            alertDialogError.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogError.setMessage("Due to Error Connection Time table is not load ! Please Try Again !");
            AlertDialog alertDialogShow = alertDialogError.create();
            alertDialogShow.show();
        }
        else {
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View layoutView = layoutInflater.inflate(R.layout.timetablelayout, null);

            TextView textView = (TextView) layoutView.findViewById(R.id.textViewMessage);
            textView.setText(message);

            ImageView imageView = (ImageView) layoutView.findViewById(R.id.imageViewTimeTable);
            imageView.setImageBitmap(bitmap);


            Button timeTableButton = (Button) layoutView.findViewById(R.id.buttonToView);
            timeTableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (bitmap != null) {
                        ImageShowActivity.setBitmap(bitmap);
                        Intent intent = new Intent(getActivity(), ImageShowActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "No image to Preview", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Button menuButton=(Button)layoutView.findViewById(R.id.rightMenuButton);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popUp = new PopupMenu(getActivity(), menuButton);
                    popUp.getMenuInflater().inflate(R.menu.timetable_menu, popUp.getMenu());
                    popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.saveButton:
                                    if (bitmap != null) {
                                        saveTimeTableToGallery(bitmap);
                                        return true;
                                    } else {
                                        Toast.makeText(getActivity(), "Nothing to save!", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                default:
                                    return false;
                            }


                        }
                    });
                    popUp.show();
                }
            });


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setView(layoutView);
            AlertDialog alertDialog1 = alertDialog.create();
            alertDialog.show();

        }

    }

    private void saveTimeTableToGallery(Bitmap bitmap) {
        String path= Environment.getExternalStorageDirectory().toString();
        File photos=new File(path+"/Download");
        if(!photos.exists()){
            photos.mkdirs();
        }
        String filename="IMG-"+System.currentTimeMillis()+".jpg";
        try {

            File file=new File(photos,filename);
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(getActivity(), "File saved !", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    private void showErrorDialog(String msg) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(msg);
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    private void showSyllabus() {



        final int[] depId = new int[1];
        final int[] semId=new int[1];

        final AlertDialog.Builder syllabusAlert=new AlertDialog.Builder(getActivity());
        syllabusAlert.setTitle("Download Syllabus Btech.");
        syllabusAlert.setMessage("Mtech. Syllabus will be provide soon !");
        syllabusAlert.setCancelable(true);
        LayoutInflater layout=getActivity().getLayoutInflater();
        View customView=layout.inflate(R.layout.syllabus_layout, null);
        Spinner selectDept=(Spinner)customView.findViewById(R.id.spinnerSelectDepartment);
        Spinner selectSem=(Spinner)customView.findViewById(R.id.spinnerSelectSemester);
        final Button downButton=(Button)customView.findViewById(R.id.buttonDownload);
        downButton.setEnabled(false);



        ArrayAdapter<String> arrayAdapt=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,departmentListSyllabus);
        selectDept.setAdapter(arrayAdapt);

        ArrayAdapter<String> arrayAdaptSyllab=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,semesterList);
        selectSem.setAdapter(arrayAdaptSyllab);

        selectDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depId[0] = position;
                System.out.println("....." + depId[0]);
                if (depId[0] > 0 && semId[0] > 0) {
                    downButton.setEnabled(true);
                }
                else {
                    downButton.setEnabled(false);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semId[0] = position;
                //System.out.println("....."+semId[0]);
                if (depId[0] > 0 && semId[0] > 0) {
                    downButton.setEnabled(true);
                }
                else {
                    downButton.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        syllabusAlert.setView(customView);

        syllabusAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog1=syllabusAlert.create();
        alertDialog1.show();
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (depId[0] > 0 && semId[0] > 0) {
                    String dept = deptIdTag[depId[0]];
                    String sem = dept + "0" + semId[0];
                    new SyllabusDownload(dept,sem).execute();

                    //sendRequestForSyllabus(dept, sem);

                }
            }
        });





    }


    private void showAcademicCalender() {
      /* AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Academic Calender will be update soon!");
        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog1=alertDialog.create();
        alertDialog1.show();*/

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        StringRequest stringRequestForCalender=new StringRequest(Request.Method.GET, ScriptUrl.getAcademicCalenderUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                serverResponseForCalender(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Connection Error! Try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequestForCalender);


    }

    private void serverResponseForCalender(String s) {
        String urlResponse="";
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONObject childObject=jsonObject.getJSONObject("response");

            urlResponse=childObject.getString("url").toString();
            if(urlResponse.equalsIgnoreCase("")){
                Toast.makeText(getActivity(), "Not Found!", Toast.LENGTH_SHORT).show();
            }
            else {
                downloadCalender=new DownloadCalender();
                downloadCalender.execute(urlResponse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showLibraryFrag() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.fragment_library,null);
        alertDialog.setView(dialogView);
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog1=alertDialog.create();
        alertDialog1.show();
    }

    private void showDepartments() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Select Department");
        alertDialog.setItems(departmentList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                Intent intent;
                Departments deptt=new Departments();
                String filePath="file:///android_asset/html/";
                switch (position) {
                    case 0:
                        deptt.setUpperTitleDept("Applied Science &Humanities");
                        deptt.setWebUrl(filePath+"applied.html");
                        intent=new Intent(getActivity(),Departments.class);
                        startActivity(intent);
                        break;
                    case 1:
                        deptt.setUpperTitleDept(departmentList[position]);
                        deptt.setWebUrl(filePath+"biotech.html");
                        intent=new Intent(getActivity(),Departments.class);
                        startActivity(intent);
                        break;
                    case 2:
                        deptt.setUpperTitleDept("CSE");
                        deptt.setWebUrl(filePath + "cse.html");
                        intent=new Intent(getActivity(),Departments.class);
                        startActivity(intent);

                        break;
                    case 3:
                        deptt.setUpperTitleDept("ECE");
                        deptt.setWebUrl(filePath+"ece.html");
                        intent=new Intent(getActivity(),Departments.class);
                        startActivity(intent);
                        break;
                    case 4:
                        deptt.setUpperTitleDept("Mechanical Engineering");
                        deptt.setWebUrl(filePath+"mech.html");
                        intent=new Intent(getActivity(),Departments.class);
                        startActivity(intent);
                        break;

                    default:
                        Toast.makeText(getActivity(), "Under Working !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=alertDialog.create();
        dialog.show();
    }







  /*  private void setUpView() {
        HomeScreen homeScreen=(HomeScreen)getActivity();
        resideMenu=homeScreen.getResideMenu();

        view.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        FrameLayout frameLayout=(FrameLayout)view.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(frameLayout);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {

    }

    public class SyllabusDownload extends AsyncTask<Void,Void,Void>{

        String department;
        String semester;
        ProgressDialog progressDialog;

       public SyllabusDownload(String dept,String sem){
           department=dept;
           semester=sem;

       }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Download");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            sendRequestForSyllabus(department,semester);
            return null;
        }

        private void sendRequestForSyllabus(final String dept, final String sem) {
            //  Toast.makeText(getActivity(), " dept :"+dept+"\n"+"sem :"+sem, Toast.LENGTH_LONG).show();
            if(!(dept.equalsIgnoreCase("")) && !(sem.equalsIgnoreCase(""))){
                StringRequest stringRequest=new StringRequest(Request.Method.POST,ScriptUrl.getSyllabusBtechUrl(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //  Toast.makeText(getActivity(), "res :"+s, Toast.LENGTH_LONG).show();
                        serverResponse(s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Connection Error Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> param=new HashMap<>();
                        param.put("deptId",dept);
                        param.put("semId", sem);
                        //System.out.println("param :"+param);

                        return param;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }

        }
        private void serverResponse(String s) {
            System.out.println("Response :" + s);


            try {
                JSONObject jsonObject=new JSONObject(s);
                String urlSyllabus=jsonObject.getString("url");
                // System.out.println("Url syllabus :"+urlSyllabus);
                actionPerform(urlSyllabus);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void actionPerform(String urlSyllabus) {
            if(urlSyllabus.equalsIgnoreCase("") || urlSyllabus.equalsIgnoreCase("Not Found!")){
                Toast.makeText(getActivity(),"Syllabus Provide soon!", Toast.LENGTH_SHORT).show();
            }
            else {


                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlSyllabus));
                startActivity(intent);

                /*try {
                    progressDialog.dismiss();
                    URL urlDown=new URL(urlSyllabus);
                    HttpURLConnection httpUrl=(HttpURLConnection)urlDown.openConnection();
                    httpUrl.setRequestMethod("GET");
                    httpUrl.setDoOutput(true);
                    httpUrl.connect();

                    File sdCardFile = new File(Environment.getExternalStorageDirectory().getPath(), "ACE/Syllabus");
                    if (!sdCardFile.exists()) {
                        sdCardFile.mkdirs();
                    }
                    File file = new File(sdCardFile,"Syllabus-"+System.currentTimeMillis()+".pdf");
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    InputStream inputStream = httpUrl.getInputStream();

                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;

                    while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                        fileOutput.write(buffer, 0, bufferLength);
                    }
                    fileOutput.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }
        }

    }

    private class DownloadImage extends AsyncTask<Void,Void,Bitmap> {

        private String urlPath;
        private String messageDisplay;
        ProgressDialog progressDialog;

        public DownloadImage(String url, String message) {
            this.urlPath=url;
            this.messageDisplay=message;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

                Bitmap data=null;
                data= downloadImage(urlPath);
                return data;
        }

        private Bitmap downloadImage(String urlPath) {

            Bitmap myBitmap=null;

            try
            {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input =null;
                if(input!=null){
                    input.reset();
                }
                input=connection.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;

                myBitmap=BitmapFactory.decodeStream(input,null,options);
                //myBitmap = BitmapFactory.decodeStream(input);

             //   String data1 = String.valueOf(String.format("/sdcard/Downloads/%d.jpg", System.currentTimeMillis()));

              //  FileOutputStream stream = new FileOutputStream(data1);

                /*ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outstream);
                byteArray = outstream.toByteArray();*/

              //  stream.write(byteArray);
              //  stream.close();
//                BufferedInputStream buffer=new BufferedInputStream(input);
//                ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
//                byte[] data=new byte[1024];
//                int current=0;
//                while ((current=buffer.read(data,0,data.length))!=-1){
//                    byteArray.write(data,0,current);
//                }
//                myBitmap= BitmapFactory.decodeByteArray(data,0,data.length);



                System.out.println("Downloading Completed");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            catch (OutOfMemoryError error){
                System.out.println("Out of memory error");
                error.printStackTrace();
            }
            return myBitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Download");
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadImage.cancel(true);
                }
            });
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bytes) {
            super.onPostExecute(bytes);
            if(!isCancelled()){
                progressDialog.dismiss();
                // Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                timeTableView(messageDisplay, bytes);
            }
        }
    }

    private void calenderView(Bitmap bitmap) {
        if(bitmap!=null){
            AcademicCalender.setBitmap(bitmap);
            Intent intent=new Intent(getActivity(),AcademicCalender.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getActivity(), "Download Failed !Try Again.", Toast.LENGTH_SHORT).show();
        }
    }

    public class DownloadCalender extends AsyncTask<String,Void,Bitmap>{

        ProgressDialog progressDialog;
        boolean progressDownload=true;
        public DownloadCalender(){

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmapData=null;
            while (progressDownload) {
                bitmapData = downloadCalender(params[0]);
                return bitmapData;
            }
            return bitmapData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Download");
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    System.out.println("cancelled");
                    downloadCalender.cancel(true);
                }
            });
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (!isCancelled()){
                progressDialog.dismiss();
                //calenderView(bitmap);
                if(bitmap!=null){
                    AcademicCalender.setBitmap(bitmap);
                    Intent intent=new Intent(getActivity(),AcademicCalender.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Download Failed !Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), "Download Cancelled!", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {
            System.out.println("cancelled");
            progressDownload=false;
        }

        private Bitmap downloadCalender(String urlPath) {
            Bitmap myBitmap=null;

            try
            {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input =null;
                if (input!=null){
                    input.reset();
                }
                input=connection.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;

                myBitmap=BitmapFactory.decodeStream(input,null,options);

                System.out.println("Downloading Completed");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            catch (OutOfMemoryError error){
                System.out.println("Out of memory error");
                error.printStackTrace();
            }
            return myBitmap;
        }
        }
}
