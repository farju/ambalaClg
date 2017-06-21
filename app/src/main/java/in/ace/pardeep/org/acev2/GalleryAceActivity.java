package in.ace.pardeep.org.acev2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryAceActivity extends AppCompatActivity {

    TextView textView;
    WebView webView;
    Button buttonForBack;
    ImageButton button;

    ProgressBar progreesBar;
    NetworkInfo wifi, mobile;
    ConnectivityManager connectivityManager;
    private static boolean isConnected;

    SharedPreferences sharedPreferences;

    View view;
    private static String LoadUrl = "http://139.59.74.116:3000/api/gallery";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_ace);


        button = (ImageButton) findViewById(R.id.buttonForRefresh);
        buttonForBack = (Button) findViewById(R.id.backButtonFees);
        progreesBar = (ProgressBar) findViewById(R.id.feesProgressBar);
        progreesBar.setMax(100);

        checkConnectivity();


        buttonForBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        webView = (WebView) findViewById(R.id.webViewGallery);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        //webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        /*webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);*/
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new MyWebViewClient());

        openWebView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();
                openWebView();
            }
        });


    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery_ace, menu);
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
    }*/

    private void openWebView() {

        if (isConnected) {
            System.out.println("webview....");
            progreesBar.setVisibility(View.VISIBLE);
            //webView.loadUrl(LoadUrl);
            button.setVisibility(View.GONE);
            webView.loadUrl(LoadUrl);

            progreesBar.setProgress(0);
            // progreesBar.setVisibility(View.GONE);
        } else {
            Toast.makeText(GalleryAceActivity.this, "Connection Error! Try Again", Toast.LENGTH_SHORT).show();
            button.setVisibility(View.VISIBLE);
            progreesBar.setVisibility(View.GONE);
        }

    }

    private void checkConnectivity() {

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        isConnected = (wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected());

    }


    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            super.onProgressChanged(view, newProgress);

        }


    }

    private void setValue(int newProgress) {
        progreesBar.setProgress(newProgress);
        System.out.println(newProgress);
        if(progreesBar.getProgress()==100){
            progreesBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
