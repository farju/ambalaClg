package in.ace.pardeep.org.acev2;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pardeep on 15-08-2016.
 */
public class FeesActivity extends Activity implements View.OnClickListener {

    TextView textView;
    WebView webView;
    Button buttonForBack;

    ImageButton button;

    private static String LoadUrl="";
    private static String upperText="";

    ProgressBar progreesBar;
    NetworkInfo wifi,mobile;
    ConnectivityManager connectivityManager;
    private static boolean isConnected;

    public static void setLoadUrl(String loadUrl) {
        LoadUrl = loadUrl;
    }

    public static void setUpperText(String upperText) {
        FeesActivity.upperText = upperText;
    }

    public FeesActivity(){

    }

    public FeesActivity(String loadUrl, String upperText) {
        LoadUrl = loadUrl;
        this.upperText = upperText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        textView=(TextView)findViewById(R.id.textViewFees);
        button=(ImageButton)findViewById(R.id.buttonForRefresh);
        buttonForBack=(Button)findViewById(R.id.backButtonFees);
        progreesBar=(ProgressBar)findViewById(R.id.feesProgressBar);
        progreesBar.setMax(100);

        checkConnectivity();


        buttonForBack.setOnClickListener(this);
        webView=(WebView)findViewById(R.id.webViewFees);
        webView.setWebChromeClient(new MyWebViewClient());



        if(!upperText.equalsIgnoreCase("")){
            textView.setText(upperText);
        }
        openWebView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();
                openWebView();
            }
        });
    }

    private void checkConnectivity() {
        connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        isConnected=(wifi!=null && wifi.isConnected())||(mobile!=null && mobile.isConnected());
    }

    private void openWebView() {
        if(!LoadUrl.equalsIgnoreCase("")){
            if(isConnected) {
                System.out.println("webview....");
                progreesBar.setVisibility(View.VISIBLE);
                //webView.loadUrl(LoadUrl);
                button.setVisibility(View.GONE);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webView.loadUrl(LoadUrl);

                FeesActivity.this.progreesBar.setProgress(0);
                // progreesBar.setVisibility(View.GONE);
            }
            else {
                Toast.makeText(FeesActivity.this, "Connection Error! Try Again", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
                progreesBar.setVisibility(View.GONE);
            }




        }

    }

    @Override
    public void onClick(View v) {
        if(v==buttonForBack){
            onBackPressed();
        }
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            FeesActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);

        }

    }
    public void setValue(int progress){
        this.progreesBar.setProgress(progress);
        if(progreesBar.getProgress()==100){
            progreesBar.setVisibility(View.GONE);
        }
    }
}
