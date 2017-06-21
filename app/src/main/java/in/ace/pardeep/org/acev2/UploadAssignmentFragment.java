package in.ace.pardeep.org.acev2;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadAssignmentFragment extends Fragment {

    TextView textView;
    WebView webView;
    Button buttonForBack;
    ImageButton button;

    ProgressBar progreesBar;
    NetworkInfo wifi,mobile;
    ConnectivityManager connectivityManager;
    private static boolean isConnected;

    SharedPreferences sharedPreferences;

    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private String facultyId="";


    View view;
    private static String LoadUrl="";

    public UploadAssignmentFragment() {
        // Required empty public constructor
         }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upload_assignment, container, false);

        sharedPreferences=getActivity().getSharedPreferences(FacultyPortal.getPrefFaculty(), 0);
        facultyId=sharedPreferences.getString("facultyid",null);
        LoadUrl="http://139.59.74.116:3000/faculty/api/uploadassignment?apiKey=e4873a6c0493e1fccb00c82f481b59169c51107c5542e449870ad5ce2add7895"+"&facultyId="+facultyId;


       /* if(!facultyId.equalsIgnoreCase("")){
            LoadUrl=LoadUrl+"&facultyId="+facultyId;
        }*/

        System.out.println(facultyId);
        System.out.println(LoadUrl);

        button=(ImageButton)view.findViewById(R.id.buttonForRefresh);
        buttonForBack=(Button)view.findViewById(R.id.backButtonFees);
        progreesBar=(ProgressBar)view.findViewById(R.id.feesProgressBar);
        progreesBar.setMax(100);

        checkConnectivity();


        buttonForBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                onDestroy();
            }
        });
        webView=(WebView)view.findViewById(R.id.webViewFees);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        //webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new MyWebViewClient());
        webView.setWebViewClient(new WebViewClient());

        openWebView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();
                openWebView();
            }
        });


        return  view;
    }

    private void openWebView() {

        if(isConnected) {
            System.out.println("webview....");
            progreesBar.setVisibility(View.VISIBLE);
            //webView.loadUrl(LoadUrl);
            button.setVisibility(View.GONE);
            webView.loadUrl(LoadUrl);

            progreesBar.setProgress(0);
            // progreesBar.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(getActivity(), "Connection Error! Try Again", Toast.LENGTH_SHORT).show();
            button.setVisibility(View.VISIBLE);
            progreesBar.setVisibility(View.GONE);
        }

    }

    private void checkConnectivity() {

        connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        isConnected=(wifi!=null && wifi.isConnected())||(mobile!=null && mobile.isConnected());

    }


    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            super.onProgressChanged(view, newProgress);

        }



        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {

            this.openFileChooser(uploadMsg);

        }


        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {

            this.openFileChooser(uploadMsg);

        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {

            mUploadMessage = uploadMsg;

            pickFile();

        }


    }

    private void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);

        chooserIntent.setType("file/*");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    private void setValue(int newProgress) {
        progreesBar.setProgress(newProgress);
        if(progreesBar.getProgress()==100){
            progreesBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILECHOOSER_RESULTCODE) {
           /* if (null == mUploadMessage)
                return;
            Uri result = data == null  ? null
                    : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;*/

           /* mUploadMessage.onReceiveValue(data.getData());
            mUploadMessage=null;*/
            //if (null == mUploadMessage) return;
            System.out.println(resultCode);
            System.out.println(data);
            Uri result = data == null || resultCode !=getActivity().RESULT_OK ? null
                    : data.getData();
            System.out.println("res :"+result);
            mUploadMessage.onReceiveValue(result);
            //mUploadMessage = null;

        }
    }
}
